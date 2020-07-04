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

public class PayDayTransaction implements Transaction {
    private final LocalDate payDate;
    private final Map<Long, PayCheck> payChecks;

    public PayDayTransaction( LocalDate payDate ) {
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

    public PayCheck getPayCheck( Long empId ) {
        // TODO should we return something else if the id doesn't exists?
        return payChecks.get( empId );
    }

    public List<PayCheck> getPayChecks() {
        return new ArrayList<>( payChecks.values() );
    }
}
