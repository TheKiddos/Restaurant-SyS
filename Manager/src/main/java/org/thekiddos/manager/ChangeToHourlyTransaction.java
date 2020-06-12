package org.thekiddos.manager;

import org.thekiddos.manager.models.HourlyClassification;
import org.thekiddos.manager.models.PaymentClassification;
import org.thekiddos.manager.models.PaymentSchedule;
import org.thekiddos.manager.models.WeeklySchedule;

public class ChangeToHourlyTransaction extends ChangeClassificationTransaction {
    private double hourlyRate;

    public ChangeToHourlyTransaction( Long empId, double hourlyRate ) {
        super( empId );
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification getPaymentClassification() {
        return new HourlyClassification( hourlyRate );
    }

    @Override
    protected PaymentSchedule getPaymentSchedule() {
        return new WeeklySchedule();
    }
}
