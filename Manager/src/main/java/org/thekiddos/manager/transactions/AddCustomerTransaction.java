package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.repositories.Database;

/**
 * Used to add a {@link Customer}.
 * The Id provided must be unique or an {@link IllegalArgumentException} will be thrown in the
 * constructor
 */
public class AddCustomerTransaction implements Transaction {
    private Customer customer;

    /**
     * @param customerId Must be unique
     * @throws IllegalArgumentException if the a customer with the provided id already exists
     */
    public AddCustomerTransaction( Long customerId, String firstName, String lastName ) {
        if ( Database.getCustomerById( customerId ) != null )
            throw new IllegalArgumentException( "A customer with " + customerId + " already exists" );
        customer = new Customer( customerId, firstName, lastName );
    }

    /**
     * Adds the customer
     */
    @Override
    public void execute() {
        Database.addCustomer( customer );
    }
}
