package org.thekiddos.manager.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thekiddos.manager.repositories.Database;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A service that a customer can request
 */
@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class Service {
    // TODO this won't allow a customer to request more than one service a day fix it (Move the service to archive or mark it as done)
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

    /**
     * Add an {@link Item} to the current service
     * @param itemId
     */
    public void addItem( Long itemId ) {
        order.addItem( Database.getItemById( itemId ) );
    }

    /**
     * @return The service total charges which includes the order charges plus additional fees (reservations, tables, ...etc)
     */
    public double getTotal() {
        return order.getTotal() + getFees();
    }

    /**
     * @return Additional fees for the service
     */
    protected abstract double getFees();

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Service service = (Service) o;
        return serviceId.equals( service.serviceId ) &&
                serviceTime.equals( service.serviceTime );
    }

    @Override
    public int hashCode() {
        return Objects.hash( serviceId, serviceTime );
    }
}
