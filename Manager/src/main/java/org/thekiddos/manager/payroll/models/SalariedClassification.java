package org.thekiddos.manager.payroll.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class SalariedClassification extends PaymentClassification {
    @NonNull
    double salary;

    @Override
    public double calculatePay( LocalDate startData, LocalDate endDate ) {
        return salary;
    }

    @Override
    public String getType() {
        return "Salaried Employee";
    }

    @Override
    public String getBaseSalary() {
        return salary + " per Month";
    }

    @Override
    public String toString() {
        return "Salary: " + salary;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        SalariedClassification that = (SalariedClassification) o;
        return Double.compare( that.salary, salary ) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash( salary );
    }
}
