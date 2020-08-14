package org.thekiddos.manager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thekiddos.manager.repositories.Database;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Table(name = "reservations")
public class Reservation extends Service {
    private static final Duration overdueThreshold = Duration.ofMinutes( 15 ); // A quick searh on the internet shows that this is the default time for most restaurants, But maybe we should make it customizable

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
        return reservationFee + table.getFee();
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

    public double getTableFee() {
        return table.getFee();
    }

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

    public boolean isOverdue() {
        if ( isActive() )
            return false;

        LocalDateTime reservationDateTime = LocalDateTime.of( getDate(), getTime() );
        Duration overdueDuration = Duration.between( reservationDateTime, LocalDateTime.now() );

        return overdueDuration.compareTo( overdueThreshold ) >= 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash( super.hashCode(), table );
    }
}
