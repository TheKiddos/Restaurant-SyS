package org.thekiddos.manager.models;

import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Service {
    protected Long customerId;
    protected LocalDate serviceDate;
    protected LocalTime serviceTime;
    private Order order = new Order();

    public Service( Long customerId, LocalDate serviceDate, LocalTime serviceTime ) {
        this.customerId = customerId;
        this.serviceDate = serviceDate;
        this.serviceTime = serviceTime;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public java.time.LocalDate getDate() {
        return this.serviceDate;
    }

    public java.time.LocalTime getTime() {
        return this.serviceTime;
    }

    public void addItem( Long itemId ) {
        order.addItem( Database.getItemById( itemId ) );
    }

    public Order getOrder() {
        return order;
    }

    public double getTotal() {
        return order.getTotal() + getFees();
    }

    protected abstract double getFees();
}
