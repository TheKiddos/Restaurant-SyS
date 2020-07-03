package org.thekiddos.manager.payroll.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class SalariedClassification implements PaymentClassification {
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
