package org.thekiddos.manager.payroll.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SickClassification extends PaymentClassification {
    private double compensation;

    @Override
    public double calculatePay( LocalDate startData, LocalDate endDate ) {
        return compensation;
    }

    @Override
    public String getType() {
        return "Sick Employee";
    }

    @Override
    public String getBaseSalary() {
        return "Compensation: " + compensation;
    }

    @Override
    public String toString() {
        return getBaseSalary();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        if ( !super.equals( o ) ) return false;
        SickClassification that = (SickClassification) o;
        return Double.compare( that.compensation, compensation ) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash( super.hashCode(), compensation );
    }

    @Override
    public double calculateCompensation() {
        return getCompensation();
    }
}
