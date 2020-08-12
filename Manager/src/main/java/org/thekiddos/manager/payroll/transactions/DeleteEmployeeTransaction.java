package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.Transaction;

public class DeleteEmployeeTransaction implements Transaction {
    private final Long empId;

    /**
     * @throws IllegalArgumentException if the customer doesn't exists
     */
    public DeleteEmployeeTransaction( Long empId ) {
        if ( Database.getEmployeeById( empId ) == null )
            throw new IllegalArgumentException( "No such Employee exists" );
        this.empId = empId;
    }

    @Override
    public void execute() {
        Database.removeEmployeeById( empId );
    }
}
