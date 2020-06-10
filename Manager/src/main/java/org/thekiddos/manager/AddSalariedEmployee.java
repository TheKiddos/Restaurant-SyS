package org.thekiddos.manager;

public class AddSalariedEmployee extends AddEmployeeTransaction {
    private double salary;

    public AddSalariedEmployee( Long empId, String name, double salary ) {
        super( empId, name );
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
