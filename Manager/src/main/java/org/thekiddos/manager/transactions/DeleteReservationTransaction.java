package org.thekiddos.manager.transactions;

import lombok.Getter;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;

/**
 * Deletes a non-active reservation
 */
public class DeleteReservationTransaction implements Transaction {
    @Getter
    private final Reservation reservation;

    /**
     * @throws IllegalArgumentException if no reservation was found for the selected table and date, or if the reservation is active
     */
    public DeleteReservationTransaction( Long tableId, LocalDate reservationDate ) {
        this.reservation = Database.getTableReservationOnDate( tableId, reservationDate );

        if ( reservation == null )
            throw new IllegalArgumentException( "No reservation for the selected table was found" );

        if ( reservation.isActive() )
            throw new IllegalArgumentException( "Can't delete an active reservation from here, you need to CheckOut" );
    }

    public DeleteReservationTransaction( Reservation reservation ) {
        this.reservation = reservation;

        if ( reservation.isActive() )
            throw new IllegalArgumentException( "Can't delete an active reservation from here, you need to CheckOut" );
    }

    /**
     * Deletes the reservation
     */
    @Override
    public void execute() {
        Database.deleteReservation( reservation );
    }
}
