package org.thekiddos.manager.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScheduledReservation extends Reservation {
    private double reservationFee;
    private static int MAX_DELAY = 3;

    public ScheduledReservation( Long tableId, Long customerId, LocalDate reservationDate, LocalTime reservationTime, double reservationFee ) {
        super( tableId, customerId, reservationDate, reservationTime );

        if ( !dateTimeAfterNow( reservationDate, reservationTime ) )
            throw new IllegalArgumentException( "Can't make reservations in the past" );

        this.reservationFee = reservationFee;
    }

    private boolean dateTimeAfterNow( LocalDate reservationDate, LocalTime reservationTime ) {
        LocalDateTime reservationDateTime = LocalDateTime.of( reservationDate, reservationTime );
        LocalDateTime now = LocalDateTime.now().minusSeconds( MAX_DELAY );
        return reservationDateTime.isAfter( now );
    }
}
