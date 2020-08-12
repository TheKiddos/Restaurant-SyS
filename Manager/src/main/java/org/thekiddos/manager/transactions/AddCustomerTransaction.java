package org.thekiddos.manager.transactions;

import org.thekiddos.manager.Util;
import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.repositories.Database;

/**
 * Used to add a {@link Customer}.
 * The Id provided must be unique or an {@link IllegalArgumentException} will be thrown in the
 * constructor
 */

public class AddCustomerTransaction implements Transaction {
    private final Long customerId;
    private final String name;
    private final String password;
    private final String email;

    /**
     * @param customerId Must be unique
     * @throws IllegalArgumentException if the a customer with the provided id already exists
     */
    public AddCustomerTransaction( Long customerId, String name, String email, String password ) {
        if ( Database.getCustomerById( customerId ) != null )
            throw new IllegalArgumentException( "A customer with " + customerId + " already exists" );

        if ( !Util.validateEmail( email ) )
            throw new IllegalArgumentException( "Please enter a valid email" );

        if ( Database.getCustomerByEmail( email ) != null )
            throw new IllegalArgumentException( "The Email must be unique" );

        if ( password.length() < 8 )
            throw new IllegalArgumentException( "Password length must be at least 8" );

        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Adds the customer
     */
    @Override
    public void execute() {
        Customer customer = new Customer( customerId, name, email, Util.hashPassword( password ) );
        Database.addCustomer( customer );
    }
}
