package com.gginon.dddworkshop.domain.snackmachine;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SnackPileTest {

    @Test
    public void cannotPassInvalidQuantity() {
        assertThatThrownBy(() -> new SnackPile(null, -1, ZERO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void cannotPassNegativeAmount() {
        assertThatThrownBy(() -> new SnackPile(null, 0, new BigDecimal("-1")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void cannotPassUnchangeableAmount() {
        assertThatThrownBy(() -> new SnackPile(null, 0, new BigDecimal("0.0123")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
