package org.thekiddos.manager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thekiddos.manager.repositories.Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@javax.persistence.Table(name = "tables")
public class Table {
    @Id @Column(name = "id")
    private Long id;
    @Column(name = "capacity")
    private int maxCapacity;
    @Column(name = "fee")
    private double fee;

    public boolean isReserved( LocalDate on ) {
        return Database.getTableReservationOnDate( id, on ) != null;
    }

    public boolean hasReservation() {
        return Database.tableHasReservations( id );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Table table = (Table) o;
        return id.equals( table.id );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }
}
