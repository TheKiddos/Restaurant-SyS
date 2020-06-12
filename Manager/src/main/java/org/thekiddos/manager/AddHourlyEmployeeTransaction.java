package org.thekiddos.manager;

import org.thekiddos.manager.models.HourlyClassification;
import org.thekiddos.manager.models.PaymentClassification;
import org.thekiddos.manager.models.PaymentSchedule;
import org.thekiddos.manager.models.WeeklySchedule;

public class AddHourlyEmployeeTransaction extends AddEmployeeTransaction {
    private double hourlyRate;

    public AddHourlyEmployeeTransaction( Long empId, String name, double hourlyRate ) {
        super( empId, name );
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
