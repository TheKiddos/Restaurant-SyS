package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.Employee;

public class ChangeNameTransaction extends ChangeEmployeeTransaction {
    private final String newName;

    public ChangeNameTransaction( Long empId, String newName ) {
        super( empId );
        this.newName = newName;
    }


    @Override
    void change( Employee emp ) {
        emp.setName( newName );
    }
}
