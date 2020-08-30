package org.thekiddos.manager.payroll.transactions;

import lombok.Getter;
import lombok.Setter;
import org.thekiddos.manager.payroll.models.HourlyClassification;
import org.thekiddos.manager.payroll.models.PaymentClassification;
import org.thekiddos.manager.payroll.models.PaymentSchedule;
import org.thekiddos.manager.payroll.models.WeeklySchedule;

@Getter
@Setter
public class ChangeToHourlyTransaction extends ChangeClassificationTransaction {
    private double hourlyRate;
    private double overHoursBonusRate = 1.5;
    private int overHoursThreshold = 8;

    public ChangeToHourlyTransaction( Long empId, double hourlyRate ) {
        super( empId );
        this.hourlyRate = hourlyRate;
    }

    @Override
    PaymentClassification getPaymentClassification( PaymentClassification oldClassification ) {
        return new HourlyClassification( hourlyRate, overHoursBonusRate, overHoursThreshold );
    }

    @Override
    PaymentSchedule getPaymentSchedule( PaymentSchedule oldSchedule ) {
        return new WeeklySchedule();
    }
}
