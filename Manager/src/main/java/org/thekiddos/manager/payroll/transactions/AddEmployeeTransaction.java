package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.*;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.Transaction;

public abstract class AddEmployeeTransaction implements Transaction {
    private final Long id;
    private final String name;

    /**
     * @throws IllegalArgumentException if an employee with this id already exists
     */
    public AddEmployeeTransaction( Long id, String name ) {
        if ( Database.getEmployeeById( id ) != null )
            throw new IllegalArgumentException( "An employee with this id already exists" );
        this.id = id;
        this.name = name;
    }

    @Override
    public void execute() {
        PaymentClassification paymentClassification = getPaymentClassification();
        PaymentSchedule paymentSchedule = getPaymentSchedule();
        PaymentMethod paymentMethod = new HoldMethod();

        Employee emp = new Employee( id, name );
        emp.setPaymentClassification( paymentClassification );
        emp.setPaymentSchedule( paymentSchedule );
        emp.setPaymentMethod( paymentMethod );

        Database.addEmployee( emp );
    }

    abstract PaymentClassification getPaymentClassification();
    abstract PaymentSchedule getPaymentSchedule();
}
