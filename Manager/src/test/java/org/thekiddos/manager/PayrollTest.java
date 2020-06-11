package org.thekiddos.manager;

import org.junit.jupiter.api.Test;
import org.thekiddos.manager.models.Employee;
import org.thekiddos.manager.models.TimeCard;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PayrollTest {
    @Test
    void testAddSalariedEmployee() {
        Long empId = 1L;
        Transaction addSalariedEmployee = new AddSalariedEmployeeTransaction( empId, "Zahlt", 1000.0 );
        addSalariedEmployee.execute();

        Employee emp = Database.getEmployeeById( empId );
        assertEquals( "Zahlt", emp.getName() );

        PaymentClassification paymentClassification = emp.getPaymentClassification();
        SalariedClassification salariedClassification = (SalariedClassification)paymentClassification;
        assertNotNull( paymentClassification ); // Same if salariedClassification because casting null gives null
        assertEquals( 1000.0, salariedClassification.getSalary() );

        PaymentSchedule paymentSchedule = emp.getPaymentSchedule();
        MonthlySchedule monthlySchedule = (MonthlySchedule)paymentSchedule;
        assertNotNull( monthlySchedule );

        PaymentMethod paymentMethod = emp.getPaymentMethod();
        HoldMethod holdMethod = (HoldMethod)paymentMethod;
        assertNotNull( holdMethod );
    }

    @Test
    void testAddHourlyEmployee() {
        Long empId = 1L;
        Transaction addHourlyEmployee = new AddHourlyEmployeeTransaction( empId, "Zahlt", 8.0 );
        addHourlyEmployee.execute();

        Employee emp = Database.getEmployeeById( empId );
        assertEquals( "Zahlt", emp.getName() );

        PaymentClassification paymentClassification = emp.getPaymentClassification();
        HourlyClassification hourlyClassification = (HourlyClassification)paymentClassification;
        assertNotNull( paymentClassification );
        assertEquals( 8.0, hourlyClassification.getHourlyRate() );

        PaymentSchedule paymentSchedule = emp.getPaymentSchedule();
        WeeklySchedule weeklySchedule = (WeeklySchedule)paymentSchedule;
        assertNotNull( weeklySchedule );

        PaymentMethod paymentMethod = emp.getPaymentMethod();
        HoldMethod holdMethod = (HoldMethod)paymentMethod;
        assertNotNull( holdMethod );
    }

    @Test
    void testDeleteEmployee() {
        Long empId = 1L;
        Transaction addEmployee = new AddSalariedEmployeeTransaction( empId, "Zahlt", 1000.0 );
        addEmployee.execute();

        Employee emp = Database.getEmployeeById( empId );
        assertEquals( "Zahlt", emp.getName() );

        Transaction deleteEmployee = new DeleteEmployeeTransaction( empId );
        deleteEmployee.execute();

        Employee emp2 = Database.getEmployeeById( empId );
        assertNull( emp2 );
    }

    @Test
    void testAddTimeCardTransaction() {
        Long empId = 1L;
        Transaction addEmployee = new AddHourlyEmployeeTransaction( empId, "Zahlt", 8.0 );
        addEmployee.execute();

        LocalDate fifthOfNovember2020 = LocalDate.of( 2020, Month.NOVEMBER, 5 );
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );
        Transaction addTimeCard = new AddTimeCardTransaction( empId, fifthOfNovember2020, threeHoursAndThirtyMinutes );
        addTimeCard.execute();

        Employee emp = Database.getEmployeeById( empId );
        assertNotNull( emp );

        HourlyClassification hourlyClassification = (HourlyClassification)emp.getPaymentClassification();
        assertNotNull( hourlyClassification );

        TimeCard timeCard = hourlyClassification.getTimeCard( LocalDate.of( 2020, Month.NOVEMBER, 5 ) ); // to make sure the data class works alright
        assertNotNull( timeCard );

        assertEquals( LocalTime.of( 3, 30 ), timeCard.getTimeWorked() );
    }
}