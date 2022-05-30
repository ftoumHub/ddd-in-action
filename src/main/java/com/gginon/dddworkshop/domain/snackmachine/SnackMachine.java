package com.gginon.dddworkshop.domain.snackmachine;

import com.gginon.dddworkshop.common.AggregateRoot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

import static com.gginon.dddworkshop.domain.snackmachine.Money.*;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

@Getter
public class SnackMachine extends AggregateRoot {

    private Money moneyInside;
    private BigDecimal moneyInTransaction;
    private Map<Integer, Slot> slots;

    public SnackMachine() {
        moneyInside = NONE;
        moneyInTransaction = ZERO;
        slots = Map.of(1, new Slot(1), 2, new Slot(2), 3, new Slot(3));
    }

    public void loadMoney(Money money) {
        moneyInside = Money.add(moneyInside, money);
    }

    public void insertMoney(Money money) {
        List<Money> allowedCoins = asList(ONE_CENT, TEN_CENT, QUARTER_DOLLAR, ONE_DOLLAR, FIVE_DOLLAR, TWENTY_DOLLAR);
        if (!allowedCoins.contains(money)) {
            throw new IllegalArgumentException();
        }
        moneyInTransaction = moneyInTransaction.add(money.getAmount());
        moneyInside = Money.add(moneyInside, money);
    }

    public void returnMoney() {
        Money money = moneyInside.allocate(moneyInTransaction);
        moneyInTransaction = ZERO;
        moneyInside = Money.subtract(moneyInside, money);
    }

    public Snack buyOneSnack(int position) {
        String canBuyMessage = canBuySnackAt(position);

        if (canBuyMessage.equals("The snack pile is empty")) {
            throw new IllegalArgumentException(canBuyMessage);
        }

        if (canBuyMessage.equals("Not enough money")) {
            throw new IllegalStateException(canBuyMessage);
        }

        Slot slot = slots.get(position).decreaseSnackPileByOne();

        Money allocated = moneyInside.allocate(moneyInTransaction.subtract(slot.getSnackPrice()));
        moneyInside = Money.subtract(moneyInside, allocated);
        moneyInTransaction = ZERO;

        return slot.getSnackPile().getSnack();
    }

    public void loadSnacks(int position, SnackPile snackPile) {
        if (position > slots.size()) {
            throw new IllegalArgumentException("Only " + slots.size() + " available!");
        }
        slots.get(position).setSnackPile(snackPile);
    }

    public String canBuySnackAt(int position) {
        SnackPile snackPile = getSnackPile(position);
        if (snackPile.getQuantity() <= 0) {
            return "The snack pile is empty";
        }

        if (moneyInTransaction.compareTo(snackPile.getPrice()) < 0) {
            return "Not enough money";
        }

        if (!moneyInside.canAllocateMoney(moneyInTransaction.subtract(snackPile.getPrice()))) {
            return "Not enough change";
        }

        return "";
    }

    public SnackPile getSnackPile(int position) {
        if (position > slots.size()) {
            throw new IllegalArgumentException("Only " + slots.size() + " available!");
        }
        return slots.get(position).getSnackPile();
    }

    public List<SnackPile> getAllSnackPiles() {
        return slots.values().stream()
                .map(Slot::getSnackPile)
                .sorted(Comparator.comparingInt(SnackPile::getQuantity))
                .collect(toUnmodifiableList());
    }

    public static class SnackMachineBuilder {
        private long id;
        private Map<Integer, Slot> slots;

        public SnackMachineBuilder(long id) {
            this.id = id;
        }

        public SnackMachineBuilder withSlots(Map<Integer, Slot> slots) {
            this.slots = slots;
            return this;
        }

        public SnackMachine build() {
            SnackMachine snackMachine = new SnackMachine();
            snackMachine.setId(id);
            snackMachine.slots = slots;
            return snackMachine;
        }
    }
}
