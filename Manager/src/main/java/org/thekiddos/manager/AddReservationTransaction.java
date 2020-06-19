package org.thekiddos.manager;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
public abstract class AddReservationTransaction implements Transaction {
    @NonNull
    protected Long tableId;
    @NonNull
    protected Long customerId;
    @NonNull
    protected LocalDate reservationDate;
    @NonNull
    protected LocalTime reservationTime;

    @Override
    public void execute() {
        try {
            if ( Database.getTableById( tableId ).isReserved( reservationDate ) ) {
                return;
            }

            Reservation reservation = getReservation();
            // TODO exceptions again
            if ( reservation == null )
                return;

            Database.addReservation( reservation );
        }
        catch ( IllegalArgumentException exception ) {
            // Nothing
        }
    }

    protected abstract Reservation getReservation();
}
