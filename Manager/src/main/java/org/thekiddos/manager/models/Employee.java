package org.thekiddos.manager.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Employee {
    @NonNull
    private Long id;
    @NonNull
    private String name;

    private PaymentClassification paymentClassification;
    private PaymentSchedule paymentSchedule;
    private PaymentMethod paymentMethod;

    public boolean isPayDay( LocalDate payDay ) {
        return paymentSchedule.isPayDay( payDay );
    }

    public PayCheck payDay( LocalDate payDate ) {
        double amount = paymentClassification.calculatePay( payDate );
        PayCheck payCheck = new PayCheck( payDate, amount );
        paymentMethod.pay( payCheck );
        return payCheck;
    }
}
