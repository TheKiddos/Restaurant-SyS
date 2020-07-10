package org.thekiddos.manager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thekiddos.manager.repositories.Database;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A reservation is a {@link Service} that requires a {@link Table}
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends Service {
    private double reservationFee;
    private boolean active = false;

    @ManyToOne
    private Table table;

    /**
     * Constructs a reservation
     * note that this doesn't check for false information directly that is left for the {@link org.thekiddos.manager.transactions.Transaction}s and the database
     * @param tableId The table to be reserved, must be not reserved in the same day
     * @param customerId The Customer to be served (can't have another service that same day)
     * @param reservationDate The date the customer expects the service
     * @param reservationTime The time the customer expects the service
     * @param reservationFee Fee for the reservation
     */
    public Reservation( Long tableId, Long customerId, LocalDate reservationDate, LocalTime reservationTime, double reservationFee ) {
        super( customerId, reservationDate, reservationTime );
        table = Database.getTableById( tableId );
        this.reservationFee = reservationFee;
    }

    /**
     * Returns the total fees which includes both the {@link #reservationFee} and the {@link #getTableFee()}
     * @return total fees
     */
    @Override
    public double getFees() {
        return reservationFee + table.getTableFee();
    }

    /**
     * An active reservation means the customer is using the table now
     * @return true if the reservation is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Activate the reservation, Signaling the arrival of the customer
     */
    public void activate() {
        active = true;
    }

    /**
     * @return The fee for using the table
     */
    public double getTableFee() {
        return table.getTableFee();
    }

    /**
     * @return The id of the reserved table
     */
    public Long getReservedTableId() {
        return table.getId();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        if ( !super.equals( o ) ) return false;
        Reservation that = (Reservation) o;
        return table.equals( that.table );
    }

    @Override
    public int hashCode() {
        return Objects.hash( super.hashCode(), table );
    }
}
