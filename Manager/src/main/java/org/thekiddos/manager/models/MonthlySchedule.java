package org.thekiddos.manager.models;

import java.time.LocalDate;

public class MonthlySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDay( LocalDate payDay ) {
        LocalDate firstDayOfNextMonth = payDay.plusMonths( 1 ).withDayOfMonth( 1 );
        return payDay.plusDays( 1 ).equals( firstDayOfNextMonth );
    }
}
