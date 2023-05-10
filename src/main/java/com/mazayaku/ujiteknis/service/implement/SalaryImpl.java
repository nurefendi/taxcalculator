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
