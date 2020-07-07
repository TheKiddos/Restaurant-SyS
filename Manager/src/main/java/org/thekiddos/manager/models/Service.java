package org.thekiddos.manager.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thekiddos.manager.repositories.Database;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class Service {
    @EmbeddedId
    private ServiceId serviceId;
    private LocalTime serviceTime;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Order order = new Order();

    public Service( Long customerId, LocalDate serviceDate, LocalTime serviceTime ) {
        this.serviceId = new ServiceId( customerId, serviceDate );
        this.serviceTime = serviceTime;
    }

    public Long getCustomerId() {
        return serviceId.getCustomerId();
    }

    public java.time.LocalDate getDate() {
        return serviceId.getDate();
    }

    public java.time.LocalTime getTime() {
        return this.serviceTime;
    }

    public void addItem( Long itemId ) {
        order.addItem( Database.getItemById( itemId ) );
    }

    public double getTotal() {
        return order.getTotal() + getFees();
    }

    protected abstract double getFees();
}
