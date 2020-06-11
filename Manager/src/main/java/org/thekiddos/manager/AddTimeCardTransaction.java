package org.thekiddos.manager;

import org.thekiddos.manager.exceptions.NoSuchEmployeeException;
import org.thekiddos.manager.models.Employee;
import org.thekiddos.manager.models.TimeCard;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddTimeCardTransaction implements Transaction {
    private Long empId;
    private LocalDate date;
    private LocalTime timeWorked;

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

        hourlyClassification.addTimeCard( new TimeCard( date, timeWorked ) );
        // TODO make sure the database is updated when implementing the real database.
    }
}
