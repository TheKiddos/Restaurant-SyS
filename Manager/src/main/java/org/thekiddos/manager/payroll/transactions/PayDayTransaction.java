package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.Employee;
import org.thekiddos.manager.payroll.models.PayCheck;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This transaction finds all employees that can be paid on the passed date
 * it stores all paychecks in it so they can be verified.
 */
public class PayDayTransaction implements Transaction {
    private final LocalDate payDate;
    private final Map<Long, PayCheck> payChecks;

    /**
     *
     * @param payDate
     * @throws IllegalArgumentException if the passed date is in the past
     */
    public PayDayTransaction( LocalDate payDate ) {
        if ( LocalDate.now().isAfter( payDate ) )
            throw new IllegalArgumentException( "Can't pay for past days" );

        this.payDate = payDate;
        this.payChecks = new HashMap<>();
    }

    @Override
    public void execute() {
        List<Employee> employees = Database.getEmployees();

        for ( Employee employee : employees )
            if ( employee.isPayDay( payDate ) )
                payChecks.put( employee.getId(), employee.payDay( payDate ) );
    }

    /**
     * @param empId
     * @return returns the paycheck for the employee specified by the id, or null if the employee was not payed
     */
    public PayCheck getPayCheck( Long empId ) {
        return payChecks.get( empId );
    }

    /**
     * @return All paychecks that were created by this transaction
     */
    public List<PayCheck> getPayChecks() {
        return new ArrayList<>( payChecks.values() );
    }
}
