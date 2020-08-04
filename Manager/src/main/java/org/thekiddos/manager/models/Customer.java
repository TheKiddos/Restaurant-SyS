package org.thekiddos.manager.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thekiddos.manager.repositories.Database;

import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Customer {
    @NonNull @Id
    private Long id;
    @NonNull
    private String name;
    private String email;
    private LocalDateTime email_verified_at;
    private String password;
    private String remember_token;

    @ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinTable( name = "recommendations", joinColumns = @JoinColumn( name = "user_id" ),
    inverseJoinColumns = @JoinColumn( name = "item_id" ) )
    private Set<Item> recommendations;

    public Customer( Long id, String name, String email, String password ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Check if the customer has any current or future reservations
     * @return true if the customer has any reservation
     */
    public boolean hasReservation() {
        return Database.customerHasReservations( id );
    }

    public boolean hasDelivery() {
        return Database.getDeliveryByCustomerId( id ).size() > 0;
    }
}
