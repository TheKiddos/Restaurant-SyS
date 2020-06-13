package org.thekiddos.manager.models;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class HourlyClassification implements PaymentClassification {
    @NonNull
    private double hourlyRate;
    private double overHoursBonusRate = 1.5;
    private int overHoursThreshold = 8;
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
