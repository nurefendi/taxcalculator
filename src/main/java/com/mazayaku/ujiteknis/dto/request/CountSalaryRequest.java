package com.mazayaku.ujiteknis.dto.request;

import java.io.Serializable;
import java.util.List;

public class CountSalaryRequest implements Serializable {

    EmployeeRequest employee;
    List<ComponentSalaryRequest> komponengaji;

    public EmployeeRequest getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeRequest employee) {
        this.employee = employee;
    }

    public List<ComponentSalaryRequest> getKomponengaji() {
        return komponengaji;
    }

    public void setKomponengaji(List<ComponentSalaryRequest> komponengaji) {
        this.komponengaji = komponengaji;
    }
}
