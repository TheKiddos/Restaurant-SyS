package org.thekiddos.manager.payroll.models;

import java.time.LocalDate;

public interface PaymentSchedule {
    boolean isPayDay( LocalDate payDay );

    LocalDate getStartPayDay( LocalDate payDay );
}
