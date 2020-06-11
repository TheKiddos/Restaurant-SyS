package org.thekiddos.manager;

import org.thekiddos.manager.models.Employee;
import org.thekiddos.manager.repositories.Database;

public abstract class ChangeEmployeeTransaction implements Transaction {
    private Long empId;

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
