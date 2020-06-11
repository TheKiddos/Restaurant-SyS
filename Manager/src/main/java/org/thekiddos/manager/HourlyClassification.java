package org.thekiddos.manager;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class HourlyClassification implements PaymentClassification {
    @NonNull
    private double hourlyRate;
}
