package org.thekiddos.manager.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thekiddos.manager.repositories.Database;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Customer {
    @NonNull @Id
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;

    /**
     * Check if the customer has any current or future reservations
     * @return true if the customer has any reservation
     */
    public boolean hasReservation() {
        return Database.customerHasReservations( id );
    }
}
