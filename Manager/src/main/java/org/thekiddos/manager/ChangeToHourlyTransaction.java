package org.thekiddos.manager;

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
