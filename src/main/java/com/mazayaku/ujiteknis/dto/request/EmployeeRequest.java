package com.mazayaku.ujiteknis.dto.request;

import com.mazayaku.ujiteknis.enums.Country;
import com.mazayaku.ujiteknis.enums.MaritalStatus;
import com.mazayaku.ujiteknis.enums.Sex;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class EmployeeRequest implements Serializable {

    @NotEmpty
    private String name;

    @NotEmpty
    private Sex sex;

    @NotNull
    private MaritalStatus maritalStatus;

    @NotNull
    private Integer childs;

    @NotEmpty
    private Country country;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getChilds() {
        return childs;
    }

    public void setChilds(Integer childs) {
        this.childs = childs;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
