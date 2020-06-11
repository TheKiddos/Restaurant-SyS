package org.thekiddos.manager;

public class AddSalariedEmployeeTransaction extends AddEmployeeTransaction {
    private double salary;

    public AddSalariedEmployeeTransaction( Long empId, String name, double salary ) {
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
