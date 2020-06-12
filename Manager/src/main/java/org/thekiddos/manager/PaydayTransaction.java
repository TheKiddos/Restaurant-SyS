package org.thekiddos.manager;

import org.thekiddos.manager.models.Employee;
import org.thekiddos.manager.models.PayCheck;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaydayTransaction implements Transaction {
    LocalDate payDate;
    Map<Long, PayCheck> payChecks;

    public PaydayTransaction( LocalDate payDate ) {
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
}
