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
