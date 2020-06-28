package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.MonthlySchedule;
import org.thekiddos.manager.payroll.models.PaymentClassification;
import org.thekiddos.manager.payroll.models.PaymentSchedule;
import org.thekiddos.manager.payroll.models.SalariedClassification;

public class ChangeToSalariedTransaction extends ChangeClassificationTransaction {
    private final double salary;

    public ChangeToSalariedTransaction( Long empId, double salary ) {
        super( empId );
        this.salary = salary;
    }

    @Override
    protected PaymentClassification getPaymentClassification() {
        return new SalariedClassification( salary );
    }

    @Override
    protected PaymentSchedule getPaymentSchedule() {
        return new MonthlySchedule();
    }
}
