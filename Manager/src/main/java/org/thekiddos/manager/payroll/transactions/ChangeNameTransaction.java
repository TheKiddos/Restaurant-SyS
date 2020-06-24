package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.Employee;

public class ChangeNameTransaction extends ChangeEmployeeTransaction {
    private String newName;

    public ChangeNameTransaction( Long empId, String newName ) {
        super( empId );
        this.newName = newName;
    }


    @Override
    protected void change( Employee emp ) {
        emp.setName( newName );
    }
}
