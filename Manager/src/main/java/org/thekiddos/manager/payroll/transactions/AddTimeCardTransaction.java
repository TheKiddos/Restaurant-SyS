package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.*;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This Transaction adds a {@link TimeCard} to an hourly employee
 * A TimeCard records how many hours an employee worked on a specified date
 */
public class AddTimeCardTransaction implements Transaction {
    private final Employee emp;
    private final LocalDate date;
    private final LocalTime timeWorked;
    private final HourlyClassification hourlyClassification;

    /**
     * @throws IllegalArgumentException if the Employee doesn't exists or if the employee isn't hourly
     */
    public AddTimeCardTransaction( Long empId, LocalDate date, LocalTime timeWorked ) {
        emp = Database.getEmployeeById( empId );
        if ( emp == null )
            throw new IllegalArgumentException( "No such Employee exists" );

        PaymentClassification paymentClassification = emp.getPaymentClassification();
        if ( !( paymentClassification instanceof HourlyClassification ) )
            throw new IllegalArgumentException( "Can't add time card to non-hourly employees" );

        hourlyClassification = (HourlyClassification)paymentClassification;

        this.date = date;
        this.timeWorked = timeWorked;
    }

    /**
     * Adds the timeCard
     */
    @Override
    public void execute() {
        TimeCard timeCard = new TimeCard( new TimeCardId( hourlyClassification.getId(), date ), timeWorked );
        Database.addTimeCard( timeCard );
    }
}
