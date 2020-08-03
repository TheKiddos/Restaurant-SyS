package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.models.Delivery;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class ImmediateDeliveryTransaction implements Transaction {
    private Long customerId;
    private final String address;
    private final double fee;
    private final Set<Item> items;
    private final LocalDate deliveryDate = LocalDate.now();
    private final LocalTime deliveryTime = LocalTime.now();

    public ImmediateDeliveryTransaction( Long customerId, String address, double fee, Set<Item> items ) {
        setCustomerId( customerId );
        this.address = address;
        this.fee = fee;
        this.items = items;
    }

    private void setCustomerId( Long customerId ) {
        Customer customer = Database.getCustomerById( customerId );

        if ( customer == null )
            throw new IllegalArgumentException( "No customer exists with an id of " + customerId );

        if ( customer.hasDelivery() )
            throw new IllegalArgumentException( "Customer " + customerId + " can't make more than another delivery until the first delivery has arrived/canceled." );

        this.customerId = customerId;
    }

    @Override
    public void execute() {
        Delivery delivery = new Delivery( customerId, deliveryDate, deliveryTime, address, fee, items );
        Database.addDelivery( delivery );
    }
}
