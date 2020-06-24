package org.thekiddos.manager.payroll.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class HourlyClassification implements PaymentClassification {
    @NonNull
    private double hourlyRate;
    @NonNull
    private double overHoursBonusRate;
    @NonNull
    private int overHoursThreshold;
    private Map<LocalDate, TimeCard> timeCards = new HashMap<>();

    public TimeCard getTimeCard( LocalDate date ) {
        return timeCards.get( date );
    }

    public void addTimeCard( TimeCard timeCard ) {
        // TODO Should we check if the timeCard already exists?
        timeCards.put( timeCard.getDate(), timeCard );
    }

    @Override
    public double calculatePay( LocalDate startData, LocalDate endDate ) {
        double amount = 0;

        for ( LocalDate unPaidDay = startData; isBeforeOrEqual( unPaidDay, endDate ); unPaidDay = unPaidDay.plusDays( 1 ) ) {
            TimeCard timeCard = timeCards.get( unPaidDay );
            amount += calculateTimeCardPay( timeCard );
        }

        return amount;
    }

    @Override
    public String getType() {
        return "Hourly Employee";
    }

    @Override
    public String getBaseSalary() {
        return hourlyRate + " per normal hour";
    }

    private double calculateTimeCardPay( TimeCard timeCard ) {
        if ( timeCard == null )
            return 0;

        double totalHours = getTotalHours( timeCard.getTimeWorked() );
        double overHours = Math.max( 0.0, totalHours - 8.0 );
        double normalHours = totalHours - overHours;
        return normalHours * hourlyRate + overHours * hourlyRate * overHoursBonusRate;
    }

    private double getTotalHours( LocalTime timeWorked ) {
        return timeWorked.getHour() + timeWorked.getMinute() / 60.0;
    }

    private boolean isBeforeOrEqual( LocalDate unPaidDay, LocalDate endDate ) {
        return unPaidDay.isBefore( endDate ) || unPaidDay.isEqual( endDate );
    }
}
