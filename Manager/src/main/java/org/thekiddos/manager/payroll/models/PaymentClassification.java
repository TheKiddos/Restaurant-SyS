package org.thekiddos.manager.payroll.models;

import java.time.LocalDate;

public interface PaymentClassification {
    double calculatePay( LocalDate startData, LocalDate endDate );

    String getType();

    String getBaseSalary();
}
