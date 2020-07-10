package org.thekiddos.manager.payroll.models;

import javax.persistence.Entity;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class WeeklySchedule extends PaymentSchedule {
    private DayOfWeek paymentDay = DayOfWeek.SATURDAY;

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

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        WeeklySchedule that = (WeeklySchedule) o;
        return paymentDay == that.paymentDay;
    }

    @Override
    public int hashCode() {
        return Objects.hash( paymentDay );
    }
}
