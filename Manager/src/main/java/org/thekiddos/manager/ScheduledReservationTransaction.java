package org.thekiddos.manager;

import org.thekiddos.manager.models.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScheduledReservationTransaction extends AddReservationTransaction {
    private static int MAX_DELAY_SECONDS = 3;

    public ScheduledReservationTransaction( Long tableId, Long customerId, LocalDate reservationDate, LocalTime reservationTime ) {
        super( tableId, customerId, reservationDate, reservationTime );
        super.reservationFee = 10.0;

        if ( !dateTimeAfterNow( reservationDate, reservationTime ) )
            throw new IllegalArgumentException( "Can't make reservations in the past" );
    }

    @Override
    protected Reservation getReservation() {
        return new Reservation( tableId, customerId, reservationDate, reservationTime, reservationFee );
    }

    private boolean dateTimeAfterNow( LocalDate reservationDate, LocalTime reservationTime ) {
        LocalDateTime reservationDateTime = LocalDateTime.of( reservationDate, reservationTime );
        LocalDateTime now = LocalDateTime.now().minusSeconds( MAX_DELAY_SECONDS );
        return reservationDateTime.isAfter( now );
    }
}
