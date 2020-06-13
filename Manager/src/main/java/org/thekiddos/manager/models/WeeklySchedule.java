package org.thekiddos.manager.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class WeeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDay( LocalDate payDay ) {
        return payDay.getDayOfWeek().equals( DayOfWeek.SATURDAY );
    }

    @Override
    public LocalDate getStartPayDay( LocalDate payDay ) {
        return payDay.minusDays( 6 );
    }
}
