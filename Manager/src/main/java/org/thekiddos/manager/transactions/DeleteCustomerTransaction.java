package org.thekiddos.manager.transactions;

import org.thekiddos.manager.repositories.Database;

public class DeleteCustomerTransaction implements Transaction {
    private final Long customerId;

    public DeleteCustomerTransaction( Long customerId ) {
        this.customerId = customerId;
    }

    @Override
    public void execute() {
        Database.removeCustomerById( customerId );
    }
}
