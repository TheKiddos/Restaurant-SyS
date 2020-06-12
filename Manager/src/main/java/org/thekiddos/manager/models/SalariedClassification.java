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
    public double calculatePay( LocalDate payDate ) {
        return salary;
    }
}
