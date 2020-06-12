package org.thekiddos.manager.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class HourlyClassification implements PaymentClassification {
    @NonNull
    private double hourlyRate;
    private Map<LocalDate, TimeCard> timeCards = new HashMap<>();

    public TimeCard getTimeCard( LocalDate date ) {
        return timeCards.get( date );
    }

    public void addTimeCard( TimeCard timeCard ) {
        // TODO Should we check if the timeCard already exists?
        timeCards.put( timeCard.getDate(), timeCard );
    }
}
