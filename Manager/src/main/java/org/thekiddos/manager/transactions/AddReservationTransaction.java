package org.thekiddos.manager.transactions;

import lombok.Getter;
import lombok.Setter;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class AddReservationTransaction implements Transaction {
    protected Long tableId;
    protected Long customerId;
    protected LocalDate reservationDate;
    protected LocalTime reservationTime;

    @Setter
    @Getter
    protected double reservationFee;

    public AddReservationTransaction( Long tableId, Long customerId, LocalDate reservationDate, LocalTime reservationTime ) {
        if ( Database.getTableById( tableId ).isReserved( reservationDate ) )
            throw new IllegalArgumentException( "Table " + tableId + " is already reserved" );

        this.tableId = tableId;
        this.customerId = customerId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    @Override
    public void execute() {
        Database.addReservation( getReservation() );
    }

    protected abstract Reservation getReservation();
}
