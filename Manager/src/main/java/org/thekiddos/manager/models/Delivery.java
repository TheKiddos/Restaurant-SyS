package org.thekiddos.manager.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

// TODO This looks like a good candidate for the Singleton pattern
@Entity
@Getter
@NoArgsConstructor
@javax.persistence.Table(name = "deliveries")
public class Delivery extends Service {
    private String deliveryAddress;
    private double deliveryFee;

    public Delivery( Long customerId, LocalDate serviceDate, LocalTime serviceTime, String deliveryAddress, double deliveryFee, Set<Item> items ) {
        super( customerId, serviceDate, serviceTime );
        this.deliveryAddress = deliveryAddress;
        this.deliveryFee = deliveryFee;

        items.forEach( super::addItem );
    }

    @Override
    protected double getFees() {
        return deliveryFee;
    }
}
