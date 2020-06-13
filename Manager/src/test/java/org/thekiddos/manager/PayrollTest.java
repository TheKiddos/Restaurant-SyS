package org.thekiddos.manager;

import org.junit.jupiter.api.Test;
import org.thekiddos.manager.models.*;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

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

    @Test
    void testChangeNameTransaction() {
        Long empId = 1L;
        Transaction addEmployee = new AddSalariedEmployeeTransaction( empId, "Zahlt", 1000.0 );
        addEmployee.execute();

        Transaction changeName = new ChangeNameTransaction( empId, "Kiddo" );
        changeName.execute();

        Employee emp = Database.getEmployeeById( empId );
        assertNotNull( emp );
        assertEquals( "Kiddo", emp.getName() );
    }

    @Test
    void testChangeToHourlyClassification() {
        Long empId = 1L;
        Transaction addEmployee = new AddSalariedEmployeeTransaction( empId, "Zahlt", 1000.0 );
        addEmployee.execute();

        Transaction changeToHourly = new ChangeToHourlyTransaction( empId, 8.0 );
        changeToHourly.execute();

        Employee emp = Database.getEmployeeById( empId );

        PaymentClassification paymentClassification = emp.getPaymentClassification();
        HourlyClassification hourlyClassification = (HourlyClassification)paymentClassification;
        assertNotNull( paymentClassification );
        assertEquals( 8.0, hourlyClassification.getHourlyRate() );

        PaymentSchedule paymentSchedule = emp.getPaymentSchedule();
        WeeklySchedule weeklySchedule = (WeeklySchedule)paymentSchedule;
        assertNotNull( weeklySchedule );
    }

    @Test
    void testChangeToSalariedClassification() {
        Long empId = 1L;
        Transaction addEmployee = new AddHourlyEmployeeTransaction( empId, "Zahlt", 8.0 );
        addEmployee.execute();

        Transaction changeToSalaried = new ChangeToSalariedTransaction( empId, 1000.0 );
        changeToSalaried.execute();

        Employee emp = Database.getEmployeeById( empId );

        PaymentClassification paymentClassification = emp.getPaymentClassification();
        SalariedClassification salariedClassification = (SalariedClassification)paymentClassification;
        assertNotNull( paymentClassification );
        assertEquals( 1000.0, salariedClassification.getSalary() );

        PaymentSchedule paymentSchedule = emp.getPaymentSchedule();
        MonthlySchedule monthlySchedule = (MonthlySchedule)paymentSchedule;
        assertNotNull( monthlySchedule );
    }

    // Currently the system just supports the hold method but we well create the mechanism of changing for the future.
    @Test
    void testChangeToHoldMethod() {
        Long empId = 1L;
        Transaction addEmployee = new AddHourlyEmployeeTransaction( empId, "Zahlt", 8.0 );
        addEmployee.execute();

        // TODO once more methods are added we should change the method to another method and then try changing it back

        Transaction changeToHold = new ChangeToHoldTransaction( empId );
        changeToHold.execute();

        Employee emp = Database.getEmployeeById( empId );

        PaymentMethod paymentMethod = emp.getPaymentMethod();
        HoldMethod holdMethod = (HoldMethod)paymentMethod;
        assertNotNull( holdMethod );
    }

    @Test
    void testPaySingleSalariedEmployee() {
        Long empId = 1L;
        Transaction addEmployee = new AddSalariedEmployeeTransaction( empId, "Zahlt", 1000.0 );
        addEmployee.execute();

        LocalDate endOfJune2020 = LocalDate.of( 2020, Month.JUNE, 30 );
        PayDayTransaction payDay = new PayDayTransaction( endOfJune2020 );
        payDay.execute();

        PayCheck payCheck = payDay.getPayCheck( empId );
        assertNotNull( payCheck );
        assertEquals( LocalDate.of( 2020, Month.JUNE, 30 ), payCheck.getPayDate() );
        assertEquals( 1000.0, payCheck.getAmount() );
        assertEquals( "Printed", payCheck.getDetails() );
    }

    @Test void testPayingSingleSalariedEmployeeOnWrongDate() {
        Long empId = 1L;
        Transaction addEmployee = new AddSalariedEmployeeTransaction( empId, "Zahlt", 1000.0 );
        addEmployee.execute();

        LocalDate june29Year2020 = LocalDate.of( 2020, Month.JUNE, 29 );
        PayDayTransaction payDay = new PayDayTransaction( june29Year2020 );
        payDay.execute();

        PayCheck payCheck = payDay.getPayCheck( empId );
        assertNull( payCheck );
    }

    @Test
    void testPaySingleHourlyEmployeeNoTimeCards() {
        Long empId = 1L;
        Transaction addEmployee = new AddHourlyEmployeeTransaction( empId, "Zahlt", 8.0 );
        addEmployee.execute();

        LocalDate payDate = LocalDate.of( 2020, Month.JUNE, 13 ); // Saturday
        PayDayTransaction payDay = new PayDayTransaction( payDate );
        payDay.execute();

        validateHourlyPaycheck( payDay, empId, payDate, 0.0 );
    }

    @Test
    void testPaySingleHourlyEmployeeOneTimeCard() {
        Long empId = 1L;
        Transaction addEmployee = new AddHourlyEmployeeTransaction( empId, "Zahlt", 8.0 );
        addEmployee.execute();

        LocalDate payDate = LocalDate.of( 2020, Month.JUNE, 13 ); // Saturday
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );

        Transaction addTimeCard = new AddTimeCardTransaction( empId, payDate, threeHoursAndThirtyMinutes );
        PayDayTransaction payDay = new PayDayTransaction( payDate );

        addTimeCard.execute();
        payDay.execute();

        validateHourlyPaycheck( payDay, empId, payDate, 28.0 );
    }

    @Test
    void testPaySingleHourlyEmployeeOverTimeOneTimeCard() {
        Long empId = 1L;
        Transaction addEmployee = new AddHourlyEmployeeTransaction( empId, "Zahlt", 8.0 );
        addEmployee.execute();

        LocalDate payDate = LocalDate.of( 2020, Month.JUNE, 13 ); // Saturday
        LocalTime nineHours = LocalTime.of( 9, 0 );

        Transaction addTimeCard = new AddTimeCardTransaction( empId, payDate, nineHours );
        PayDayTransaction payDay = new PayDayTransaction( payDate );

        addTimeCard.execute();
        payDay.execute();

        // 9 hours of work means 8 hours are paid normally while the extra hour is paid 1.5 times the normal hour
        validateHourlyPaycheck( payDay, empId, payDate, ( 8.0 + 1.5 ) * 8.0 );
    }

    @Test
    void testPaySingleHourlyEmployeeOnWrongDate() {
        Long empId = 1L;
        Transaction addEmployee = new AddHourlyEmployeeTransaction( empId, "Zahlt", 8.0 );
        addEmployee.execute();

        LocalDate payDate = LocalDate.of( 2020, Month.JUNE, 12 ); // Friday
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );

        Transaction addTimeCard = new AddTimeCardTransaction( empId, payDate, threeHoursAndThirtyMinutes );
        PayDayTransaction payDay = new PayDayTransaction( payDate );

        addTimeCard.execute();
        payDay.execute();

        PayCheck payCheck = payDay.getPayCheck( empId );
        assertNull( payCheck );
    }

    @Test
    void testPaySingleHourlyEmployeeTwoTimeCard() {
        Long empId = 1L;
        Transaction addEmployee = new AddHourlyEmployeeTransaction( empId, "Zahlt", 8.0 );
        addEmployee.execute();

        LocalDate beforePayDate = LocalDate.of( 2020, Month.JUNE, 12 ); // Friday
        LocalDate payDate = LocalDate.of( 2020, Month.JUNE, 13 ); // Saturday
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );
        LocalTime fourHours = LocalTime.of( 4, 0 );

        Transaction addTimeCard = new AddTimeCardTransaction( empId, beforePayDate, threeHoursAndThirtyMinutes );
        Transaction addTimeCard2 = new AddTimeCardTransaction( empId, payDate, fourHours );
        PayDayTransaction payDay = new PayDayTransaction( payDate );

        addTimeCard.execute();
        addTimeCard2.execute();
        payDay.execute();

        validateHourlyPaycheck( payDay, empId, payDate, 60.0 );
    }

    @Test
    void testPaySingleHourlyEmployeeWithTimeCardsSpanningThreePayPeriods() {
        Long empId = 1L;
        Transaction addEmployee = new AddHourlyEmployeeTransaction( empId, "Zahlt", 8.0 );
        addEmployee.execute();

        LocalDate afterPayDate = LocalDate.of( 2020, Month.JUNE, 14 ); // Sunday
        LocalDate payDate = LocalDate.of( 2020, Month.JUNE, 13 ); // Saturday
        LocalDate previousPayDate = LocalDate.of( 2020, Month.JUNE, 6 ); // Previous Saturday
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );
        LocalTime fourHours = LocalTime.of( 4, 0 );

        Transaction addTimeCard = new AddTimeCardTransaction( empId, payDate, threeHoursAndThirtyMinutes ); // the only valid one
        Transaction addTimeCard2 = new AddTimeCardTransaction( empId, afterPayDate, fourHours );
        Transaction addTimeCard3 = new AddTimeCardTransaction( empId, previousPayDate, fourHours );
        PayDayTransaction payDay = new PayDayTransaction( payDate );

        addTimeCard.execute();
        addTimeCard2.execute();
        addTimeCard3.execute();
        payDay.execute();

        validateHourlyPaycheck( payDay, empId, payDate, 28.0 );
    }

    private void validateHourlyPaycheck( PayDayTransaction payDay, Long empId, LocalDate payDate, double expectedAmount ) {
        PayCheck payCheck = payDay.getPayCheck( empId );
        assertNotNull( payCheck );
        assertEquals( expectedAmount, payCheck.getAmount() );
        assertEquals( "Printed", payCheck.getDetails() );
    }

    // TODO When changing an employee classification what should happen to his current pay for example if he was hourly paid?
}