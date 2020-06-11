package org.thekiddos.manager;

import org.thekiddos.manager.models.Employee;

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
