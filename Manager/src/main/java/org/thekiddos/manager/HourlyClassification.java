package org.thekiddos.manager;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thekiddos.manager.models.TimeCard;

import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
