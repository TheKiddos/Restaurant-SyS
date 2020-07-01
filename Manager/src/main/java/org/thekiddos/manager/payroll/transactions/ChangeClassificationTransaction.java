package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.Employee;
import org.thekiddos.manager.payroll.models.PaymentClassification;
import org.thekiddos.manager.payroll.models.PaymentSchedule;

public abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {
    public ChangeClassificationTransaction( Long empId ) {
        super( empId );
    }

    @Override
    protected void change( Employee emp ) {
        emp.setPaymentClassification( getPaymentClassification() );
        emp.setPaymentSchedule( getPaymentSchedule() );
    }

    protected abstract PaymentClassification getPaymentClassification();
    protected abstract PaymentSchedule getPaymentSchedule();
}