package org.thekiddos.manager.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thekiddos.manager.PaymentClassification;
import org.thekiddos.manager.PaymentMethod;
import org.thekiddos.manager.PaymentSchedule;

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
}
