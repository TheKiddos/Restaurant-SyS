package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.Employee;
import org.thekiddos.manager.payroll.models.HourlyClassification;
import org.thekiddos.manager.payroll.models.TimeCard;
import org.thekiddos.manager.payroll.models.TimeCardId;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddTimeCardTransaction implements Transaction {
    private final Long empId;
    private final LocalDate date;
    private final LocalTime timeWorked;

    public AddTimeCardTransaction( Long empId, LocalDate date, LocalTime timeWorked ) {
        this.empId = empId;
        this.date = date;
        this.timeWorked = timeWorked;
    }

    @Override
    public void execute() {
        Employee emp = Database.getEmployeeById( empId );
        // TODO fix this exception thingy
        //if ( emp == null )
        //    throw new NoSuchEmployeeException( empId );

        HourlyClassification hourlyClassification = (HourlyClassification)emp.getPaymentClassification();

        //if( hourlyClassification == null )
        //    throw new IllegalArgumentException( "Can't add a TimeCard to a non-hourly employee" );
        TimeCard timeCard = new TimeCard( new TimeCardId( hourlyClassification.getId(), date ), timeWorked );
        hourlyClassification.addTimeCard( timeCard );
        Database.addEmployee( emp );
    }
}
