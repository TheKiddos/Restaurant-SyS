package org.thekiddos.manager;

public class AddHourlyEmployee extends AddEmployeeTransaction {
    private double hourlyRate;

    public AddHourlyEmployee( Long empId, String name, double hourlyRate ) {
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
