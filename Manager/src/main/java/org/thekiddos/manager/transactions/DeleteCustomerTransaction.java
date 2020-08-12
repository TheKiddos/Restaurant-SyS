package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.repositories.Database;

/**
 * Used to delete a {@link Customer} (the customer can't have an {@link org.thekiddos.manager.models.Reservation})
 */
public class DeleteCustomerTransaction implements Transaction {
    private final Customer customer;

    /**
     * @param customerId the customerId
     * @throws IllegalArgumentException if the customer does't exists of if the customer has a reservation
     */
    public DeleteCustomerTransaction( Long customerId ) {
        customer = Database.getCustomerById( customerId );

        if ( customer == null )
            throw new IllegalArgumentException( "No Customer with id: " + customerId + " found" );

        if ( customer.hasReservation() )
            throw new IllegalArgumentException( "Customer " + customerId + " has a reservation and therefore can't be deleted before deleting the reservation" );
    }

    /**
     * Deletes the customer
     */
    @Override
    public void execute() {
        Database.removeCustomer( customer );
    }
}
