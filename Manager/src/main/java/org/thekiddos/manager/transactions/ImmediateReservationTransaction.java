package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Creates a immediate {@link Reservation}
 * Which means that the customer is already here
 */
public class ImmediateReservationTransaction extends AddReservationTransaction {

    /**
     * @throws IllegalArgumentException if the customer, table don't exists or if the table is reserved, customer has a reservation today
     */
    public ImmediateReservationTransaction( Long tableId, Long customerId ) {
        super( tableId, customerId, LocalDate.now(), LocalTime.now() );
        setReservationFee( 0.0 );
    }

    @Override
    Reservation getReservation() {
        Reservation reservation = new Reservation( getTableId(), getCustomerId(), getReservationDate(), getReservationTime(), getReservationFee() );
        reservation.activate();
        return reservation;
    }
}
