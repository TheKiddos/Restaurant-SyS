package org.thekiddos.manager;

import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.repositories.Database;

public class AddCustomerTransaction implements Transaction {
    private Long customerId;
    private String firstName;
    private String lastName;

    public AddCustomerTransaction( Long customerId, String firstName, String lastName ) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void execute() {
        Customer customer = new Customer( customerId, firstName, lastName );
        Database.addCustomer( customer );
    }
}
