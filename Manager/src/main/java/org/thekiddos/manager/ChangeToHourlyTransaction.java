package org.thekiddos.manager;

import lombok.Getter;
import lombok.Setter;
import org.thekiddos.manager.models.HourlyClassification;
import org.thekiddos.manager.models.PaymentClassification;
import org.thekiddos.manager.models.PaymentSchedule;
import org.thekiddos.manager.models.WeeklySchedule;

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
    protected PaymentClassification getPaymentClassification() {
        return new HourlyClassification( hourlyRate, overHoursBonusRate, overHoursThreshold );
    }

    @Override
    protected PaymentSchedule getPaymentSchedule() {
        return new WeeklySchedule();
    }
}
