package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.Employee;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.Transaction;

public abstract class ChangeEmployeeTransaction implements Transaction {
    private final Long empId;

    public ChangeEmployeeTransaction( Long empId ) {
        this.empId = empId;
    }

    abstract void change( Employee emp );

    @Override
    public void execute() {
        Employee emp = Database.getEmployeeById( empId );
        change( emp );
        Database.addEmployee( emp );
    }
}
