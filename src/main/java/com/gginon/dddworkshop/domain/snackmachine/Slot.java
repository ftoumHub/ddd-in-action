package com.gginon.dddworkshop.domain.snackmachine;

import com.gginon.dddworkshop.common.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Slot extends Entity {

    private SnackPile snackPile;
    private int position;

    public Slot() {
    }

    public Slot(int position) {
        this.snackPile = SnackPile.EMPTY;
        this.position = position;
    }

    public BigDecimal getSnackPrice() {
        return this.getSnackPile().getPrice();
    }

    public Slot decreaseSnackPileByOne() {
        this.setSnackPile(getSnackPile().subtractOne());
        return this;
    }
}
