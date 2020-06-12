package org.thekiddos.manager.models;

import java.time.LocalDate;

public interface PaymentClassification {
    double calculatePay( LocalDate payDate );
}
