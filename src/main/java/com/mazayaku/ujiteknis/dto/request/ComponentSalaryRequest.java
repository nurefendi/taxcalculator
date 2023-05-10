package com.mazayaku.ujiteknis.dto.request;

import com.mazayaku.ujiteknis.enums.TypeOfComponentSalary;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ComponentSalaryRequest {

    @NotEmpty
    private String name;

    @NotNull
    private TypeOfComponentSalary type;

    @NotNull
    private BigDecimal amount;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeOfComponentSalary getType() {
        return type;
    }

    public void setType(TypeOfComponentSalary type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
