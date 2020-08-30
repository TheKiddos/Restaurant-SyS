package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.MonthlySchedule;
import org.thekiddos.manager.payroll.models.PaymentClassification;
import org.thekiddos.manager.payroll.models.PaymentSchedule;
import org.thekiddos.manager.payroll.models.SickClassification;

public class ChangeToSickTransaction extends ChangeClassificationTransaction {

    public ChangeToSickTransaction( Long empId ) {
        super( empId );
    }

    @Override
    PaymentClassification getPaymentClassification( PaymentClassification oldClassification ) {
        return new SickClassification( oldClassification.calculateCompensation() );
    }

    @Override
    PaymentSchedule getPaymentSchedule( PaymentSchedule oldSchedule ) {
        return new MonthlySchedule();
    }
}
