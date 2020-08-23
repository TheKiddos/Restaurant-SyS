package org.thekiddos.manager.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.thekiddos.manager.repositories.Database;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableDTO implements Serializable {
    private Long id;
    private int maxCapacity;
    private double fee;

    /**
     * Check if the table has any reservations on the specified date
     * @param on The date to check
     * @return true if that table is reserved on the specified date
     */
    public boolean isReserved( LocalDate on ) {
        return Database.getTableReservationOnDate( id, on ) != null;
    }

    /**
     * @return true if the table has any reservation
     */
    public boolean hasReservation() {
        return Database.tableHasReservations( id );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        TableDTO table = (TableDTO) o;
        return id.equals( table.id );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }
}