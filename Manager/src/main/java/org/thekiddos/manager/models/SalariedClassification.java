package org.thekiddos.manager.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
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
}
