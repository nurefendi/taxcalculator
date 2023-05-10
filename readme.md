# UJI TEKNIS

## SalaryController.java
``` java
package com.mazayaku.ujiteknis.controller;


import com.mazayaku.ujiteknis.constant.ServicePath;
import com.mazayaku.ujiteknis.dto.request.CountSalaryRequest;
import com.mazayaku.ujiteknis.dto.response.CountSalaryResponse;
import com.mazayaku.ujiteknis.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalaryController {

    @Autowired
    SalaryService salaryService;


    @PostMapping(value = ServicePath.COUNT_SALARY, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CountSalaryResponse> countSalary(@RequestBody CountSalaryRequest request){


        CountSalaryResponse result = salaryService.countSalary(request);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
```
## CountSalaryResponse.java
```java
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
```

## CountSalaryRequest.java

``` java
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

```

## EmployeeRequest.java
```java
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

```
## ComponentSalaryRequest
```java
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

```
## Country
```java
package com.mazayaku.ujiteknis.enums;

public enum Country {
    indonesia,
    vietnam
}

```
## MaritalStatus
```java
package com.mazayaku.ujiteknis.enums;

public enum MaritalStatus {
    married,
    single;
}
```
## Sex
```java
package com.mazayaku.ujiteknis.enums;

public enum Sex {
    male,
    female;
}
```
## TypeOfComponentSalary
```java
package com.mazayaku.ujiteknis.enums;

public enum TypeOfComponentSalary {
    deduction, earning
}

```
## SalaryService
```java
package com.mazayaku.ujiteknis.service;

import com.mazayaku.ujiteknis.dto.request.CountSalaryRequest;
import com.mazayaku.ujiteknis.dto.response.CountSalaryResponse;

public interface SalaryService {

    CountSalaryResponse countSalary(CountSalaryRequest request);
}

```
## SalaryImpl
```java
package com.mazayaku.ujiteknis.service.implement;

import com.mazayaku.ujiteknis.dto.request.CountSalaryRequest;
import com.mazayaku.ujiteknis.dto.response.CountSalaryResponse;
import com.mazayaku.ujiteknis.enums.TypeOfComponentSalary;
import com.mazayaku.ujiteknis.service.SalaryService;
import org.springframework.stereotype.Service;


@Service
public class SalaryImpl implements SalaryService {


    @Override
    public CountSalaryResponse countSalary(CountSalaryRequest request) {

        TaxCalculator.Builder builder = new TaxCalculator.Builder();

        builder.addCountry(request.getEmployee().getCountry())
                .haveChild(request.getEmployee().getChilds())
                .maritalStatus(request.getEmployee().getMaritalStatus());

        request.getKomponengaji().forEach(componentSalaryRequest -> {
            if (componentSalaryRequest.getType().equals(TypeOfComponentSalary.earning))
                builder.addEarning(componentSalaryRequest.getAmount());

            if (componentSalaryRequest.getType().equals(TypeOfComponentSalary.deduction))
                builder.addDeduction(componentSalaryRequest.getAmount());
        });

        TaxCalculator taxCalculator = builder.build();

        CountSalaryResponse result = new CountSalaryResponse();
        result.setTaxThisMonth(taxCalculator.getTaxRate());

        return result;

    }
}

```
## TaxCalculator
```java
package com.mazayaku.ujiteknis.service.implement;

import com.mazayaku.ujiteknis.enums.Country;
import com.mazayaku.ujiteknis.enums.MaritalStatus;

import java.math.BigDecimal;

public class TaxCalculator {

    BigDecimal taxRate;

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    private TaxCalculator(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public static class Builder {
        BigDecimal earning = BigDecimal.ZERO;
        BigDecimal deduction = BigDecimal.ZERO;
        Country country;
        int numberOfChild;
        MaritalStatus maritalStatus;


        public Builder(){

        }

        public Builder addCountry(Country country){
            this.country = country;
            return this;
        }


        public Builder addEarning(BigDecimal earning){
            if (this.earning.equals(BigDecimal.ZERO)) {
                this.earning = earning;
            } else {
                this.earning = this.earning.add(earning);
            }
            return this;
        }

        public Builder addDeduction(BigDecimal deduction){
            if (this.deduction.equals(BigDecimal.ZERO)) {
                this.deduction = deduction;
            } else {
                this.deduction = this.deduction.add(deduction);
            }
            return this;
        }

        public Builder haveChild(int numberOfChild){
            this.numberOfChild = numberOfChild;
            return this;
        }


        public Builder maritalStatus(MaritalStatus maritalStatus){
            this.maritalStatus = maritalStatus;
            return this;
        }



        public TaxCalculator build() {
            BigDecimal calculatedTaxRate = calculateTaxRate();
            return new TaxCalculator(calculatedTaxRate);
        }

        private BigDecimal calculateTaxRate() {
            return calculatePTKP();
        }

        private BigDecimal calculatePTKP() {

            BigDecimal I0_50 = BigDecimal.valueOf(0.05);
            BigDecimal I50_250 = BigDecimal.valueOf(0.1);
            BigDecimal IUP250 = BigDecimal.valueOf(0.15);

            BigDecimal V0_50 = BigDecimal.valueOf(0.025);
            BigDecimal VUP50 = BigDecimal.valueOf(0.075);

            BigDecimal ptkp = this.earning.multiply(BigDecimal.valueOf(12));
            BigDecimal tarifPtkp;

            if (this.country.equals(Country.indonesia)){
                if (this.maritalStatus.equals(MaritalStatus.married)){
                    tarifPtkp = BigDecimal.valueOf(50000000);
                    if (this.numberOfChild > 0) {
                        tarifPtkp = BigDecimal.valueOf(75000000);
                    }
                } else {
                    tarifPtkp = BigDecimal.valueOf(25000000);
                }

                ptkp = ptkp.subtract(tarifPtkp);


                BigDecimal firstLayer = BigDecimal.valueOf(50000000 * 0.05);
                BigDecimal nextLayer = ptkp.subtract(BigDecimal.valueOf(50000000)).multiply(IUP250);

                BigDecimal taxInYear = firstLayer.add(nextLayer);
                return taxInYear.divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_HALF_UP);



            }
            if (this.country.equals(Country.vietnam)){
                if (this.maritalStatus.equals(MaritalStatus.married)){
                    tarifPtkp = BigDecimal.valueOf(30000000);
                } else {
                    tarifPtkp = BigDecimal.valueOf(15000000);
                }

                ptkp = ptkp.subtract(tarifPtkp)
                        .subtract(BigDecimal.valueOf(1000000 * 12));

                BigDecimal firstLayer = BigDecimal.valueOf(50000000 * 0.025);
                BigDecimal nextLayer = ptkp.subtract(BigDecimal.valueOf(50000000)).multiply(VUP50);

                BigDecimal taxInYear = firstLayer.add(nextLayer);
                return taxInYear.divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_HALF_UP);
            }



            return BigDecimal.ZERO;
        }

    }
}

```