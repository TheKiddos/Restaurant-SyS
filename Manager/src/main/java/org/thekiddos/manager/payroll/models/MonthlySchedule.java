package org.thekiddos.manager.payroll.models;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class MonthlySchedule extends PaymentSchedule {
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

    @Override
    public String toString() {
        return "Payed Every Month";
    }
}
