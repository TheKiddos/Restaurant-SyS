package org.thekiddos.manager;

import org.thekiddos.manager.models.MonthlySchedule;
import org.thekiddos.manager.models.PaymentClassification;
import org.thekiddos.manager.models.PaymentSchedule;
import org.thekiddos.manager.models.SalariedClassification;

public class ChangeToSalariedTransaction extends ChangeClassificationTransaction {
    private double salary;

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
