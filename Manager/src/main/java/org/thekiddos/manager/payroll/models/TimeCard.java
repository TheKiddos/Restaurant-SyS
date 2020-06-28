package org.thekiddos.manager.payroll.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class TimeCard {
    @NonNull
    private final LocalDate date;
    @NonNull
    private final LocalTime timeWorked;
}
