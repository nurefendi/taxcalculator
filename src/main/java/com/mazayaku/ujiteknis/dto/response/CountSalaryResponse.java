package com.mazayaku.ujiteknis.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class CountSalaryResponse implements Serializable {


    BigDecimal taxThisMonth;

    public BigDecimal getTaxThisMonth() {
        return taxThisMonth;
    }

    public void setTaxThisMonth(BigDecimal taxThisMonth) {
        this.taxThisMonth = taxThisMonth;
    }
}
