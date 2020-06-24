package org.thekiddos.manager.payroll.transactions;

import lombok.Getter;
import lombok.Setter;
import org.thekiddos.manager.payroll.models.HourlyClassification;
import org.thekiddos.manager.payroll.models.PaymentClassification;
import org.thekiddos.manager.payroll.models.PaymentSchedule;
import org.thekiddos.manager.payroll.models.WeeklySchedule;

@Getter
@Setter
public class AddHourlyEmployeeTransaction extends AddEmployeeTransaction {
    private double hourlyRate;
    private double overHoursBonusRate = 1.5;
    private int overHoursThreshold = 8;

    public AddHourlyEmployeeTransaction( Long empId, String name, double hourlyRate ) {
        super( empId, name );
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification getPaymentClassification() {
        return new HourlyClassification( hourlyRate, overHoursBonusRate, overHoursThreshold );
    }

    @Override
    protected PaymentSchedule getPaymentSchedule() {
        return new WeeklySchedule();
    }
}
