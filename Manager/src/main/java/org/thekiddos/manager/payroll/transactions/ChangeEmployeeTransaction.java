package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.Employee;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.Transaction;

public abstract class ChangeEmployeeTransaction implements Transaction {
    private final Long empId;

    public ChangeEmployeeTransaction( Long empId ) {
        this.empId = empId;
    }

    protected abstract void change( Employee emp );

    @Override
    public void execute() {
        Employee emp = Database.getEmployeeById( empId );
        // TODO EXCEPTION IF NOT FOUND HOW EVER THESE STUFF SHOULD BE IN THE DATABASE
        change( emp );
    }
}
