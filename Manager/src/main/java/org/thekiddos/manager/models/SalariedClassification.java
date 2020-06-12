package org.thekiddos.manager.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SalariedClassification implements PaymentClassification {
    @NonNull
    double salary;
}
