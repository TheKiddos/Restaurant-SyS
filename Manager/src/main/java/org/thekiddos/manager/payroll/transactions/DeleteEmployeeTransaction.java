package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.Transaction;

public class DeleteEmployeeTransaction implements Transaction {
    private final Long empId;

    public DeleteEmployeeTransaction( Long empId ) {
        this.empId = empId;
    }

    @Override
    public void execute() {
        Database.removeEmployeeById( empId );
    }
}
