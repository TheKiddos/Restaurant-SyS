package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.Employee;
import org.thekiddos.manager.payroll.models.PaymentMethod;

public abstract class ChangeMethodTransaction extends ChangeEmployeeTransaction {
    private Long empId;

    public ChangeMethodTransaction( Long empId ) {
        super( empId );
    }

    @Override
    void change( Employee emp ) {
        emp.setPaymentMethod( getPaymentMethod() );
    }

    abstract PaymentMethod getPaymentMethod();
}
