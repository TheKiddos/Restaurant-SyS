package org.thekiddos.manager;

import org.thekiddos.manager.models.Employee;
import org.thekiddos.manager.models.PaymentMethod;

public abstract class ChangeMethodTransaction extends ChangeEmployeeTransaction {
    private Long empId;

    public ChangeMethodTransaction( Long empId ) {
        super( empId );
    }

    @Override
    protected void change( Employee emp ) {
        emp.setPaymentMethod( getPaymentMethod() );
    }

    protected abstract PaymentMethod getPaymentMethod();
}
