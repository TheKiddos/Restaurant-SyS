package org.thekiddos.manager.payroll.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Data
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
}
