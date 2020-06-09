package org.thekiddos.manager;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SalariedClassification implements PaymentClassification {
    @NonNull
    double salary;
}
