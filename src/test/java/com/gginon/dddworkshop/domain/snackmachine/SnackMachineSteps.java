package com.gginon.dddworkshop.domain.snackmachine;

import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonnéque;
import io.cucumber.java.fr.Quand;

import java.math.BigDecimal;

import static com.gginon.dddworkshop.domain.snackmachine.Money.ONE_DOLLAR;
import static com.gginon.dddworkshop.domain.snackmachine.Snack.CHOCOLATE;
import static org.assertj.core.api.Assertions.assertThat;

public class SnackMachineSteps {
	
	private SnackMachine snackMachine = new SnackMachine();
	
	@Etantdonnéque("on insere un dollar")
	public void onInsereUnDollar() {
		snackMachine.insertMoney(ONE_DOLLAR);
	}

	@Etantdonnéque("le distributeur est chargé à la position {int} avec {int} friandises")
	public void leDistributeurEstChargeALaPosition(int position, int quantité) {
		snackMachine.loadSnacks(position, new SnackPile(CHOCOLATE, quantité, new BigDecimal("1")));
	}

	@Quand("l'utilisateur demande un retour monnaie")
	public void l_utilisateur_demande_un_retour_monnaie() {
		snackMachine.returnMoney();
	}

	@Alors("le montant dans la transaction est {int}")
	public void le_montant_dans_la_transaction_est(int montant) {
		assertThat(snackMachine.getMoneyInTransaction())
				.isEqualByComparingTo(new BigDecimal(montant));
	}

	@Quand("on achète une friandise à la position {int}")
	public void on_achète_une_friandise_à_la_position(int position) {
		snackMachine.buyOneSnack(position);
	}

	@Alors("le distributeur contient {int} dollar")
	public void le_distributeur_contient(int montant) {
		assertThat(snackMachine.getMoneyInside())
				.isEqualTo(new Money(0, 0, 0, montant, 0, 0));
	}

	@Alors("La quantité de friandises à la position {int} est {int}")
	public void la_quantité_de_friandises_à_la_position_est(int position, int quantité) {
		assertThat(snackMachine.getSnackPile(position).getQuantity())
				.isEqualTo(quantité);
	}
}
