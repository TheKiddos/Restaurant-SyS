package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.MonthlySchedule;
import org.thekiddos.manager.payroll.models.PaymentClassification;
import org.thekiddos.manager.payroll.models.PaymentSchedule;
import org.thekiddos.manager.payroll.models.SalariedClassification;

public class AddSalariedEmployeeTransaction extends AddEmployeeTransaction {
    private final double salary;

    /**
     * @throws IllegalArgumentException if an employee with this id already exists
     */
    public AddSalariedEmployeeTransaction( Long empId, String name, double salary ) {
        super( empId, name );
        this.salary = salary;
    }

    @Override
    PaymentClassification getPaymentClassification() {
        return new SalariedClassification( salary );
    }

    @Override
    PaymentSchedule getPaymentSchedule() {
        return new MonthlySchedule();
    }
}
