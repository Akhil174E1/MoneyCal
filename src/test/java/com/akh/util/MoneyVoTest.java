package com.akh.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MoneyVoTest {

    @Test
    void moneyVoShouldSupportCategories() {
        MoneyVo moneyVo = new MoneyVo();
        moneyVo.setCategory("Food");

        assertEquals("Food", moneyVo.getCategory());
    }
}
