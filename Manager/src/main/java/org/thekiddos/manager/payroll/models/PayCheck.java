package org.thekiddos.manager.payroll.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class PayCheck {
    @NonNull
    private final LocalDate payDate;
    @NonNull
    private final double amount;
    @NonNull
    private final String details;
    @NonNull
    private final Employee employee;
}
