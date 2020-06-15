package org.thekiddos.manager.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class PayCheck {
    @NonNull
    private LocalDate payDate;
    @NonNull
    private double amount;
    @NonNull
    private String details;
    @NonNull
    private Employee employee;
}
