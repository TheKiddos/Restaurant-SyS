package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.repositories.Database;

public class DeleteCustomerTransaction implements Transaction {
    private final Customer customer;

    public DeleteCustomerTransaction( Long customerId ) {
        customer = Database.getCustomerById( customerId );

        if ( customer == null )
            throw new IllegalArgumentException( "No Customer with id: " + customerId + " found" );

        if ( customer.hasReservation() )
            throw new IllegalArgumentException( "Customer " + customerId + " has a reservation and therefore can't be deleted before deleting the reservation" );
    }

    @Override
    public void execute() {
        Database.removeCustomer( customer );
    }
}
