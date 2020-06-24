package org.thekiddos.manager.payroll.models;

import java.time.LocalDate;

public class MonthlySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDay( LocalDate payDay ) {
        return isLastDayOfMonth( payDay );
    }

    @Override
    public LocalDate getStartPayDay( LocalDate payDay ) {
        return firstDayOfMonth( payDay );
    }

    private LocalDate firstDayOfMonth( LocalDate date ) {
        return date.withDayOfMonth( 1 );
    }

    private boolean isLastDayOfMonth( LocalDate date ) {
        LocalDate firstDayOfNextMonth = date.plusMonths( 1 ).withDayOfMonth( 1 );
        return date.plusDays( 1 ).equals( firstDayOfNextMonth );
    }
}
