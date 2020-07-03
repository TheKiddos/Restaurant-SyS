package org.thekiddos.manager.payroll.models;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeeklySchedule implements PaymentSchedule {
    private final DayOfWeek paymentDay = DayOfWeek.SATURDAY;

    @Override
    public boolean isPayDay( LocalDate payDay ) {
        return payDay.getDayOfWeek().equals( paymentDay );
    }

    @Override
    public LocalDate getStartPayDay( LocalDate payDay ) {
        return payDay.minusDays( 6 );
    }

    @Override
    public String toString() {
        return "Payed Every: " + paymentDay;
    }
}
