package org.thekiddos.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.thekiddos.manager.models.PayCheck;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class PayrollTest {
    private Long empId = 1L, empId2 = 2L;

    @BeforeEach
    void setUpDatabase() {
        Database.init();
        fillDatabase();
    }

    private void fillDatabase() {
        new AddSalariedEmployeeTransaction( empId, "Zahlt", 1000.0 ).execute();
        new AddHourlyEmployeeTransaction( empId2, "Zahlt", 8.0 ).execute();
    }

    @Test
    void testPaySingleSalariedEmployee() {
        LocalDate endOfJune2020 = LocalDate.of( 2020, Month.JUNE, 30 );
        PayDayTransaction payDay = new PayDayTransaction( endOfJune2020 );
        payDay.execute();

        PayCheck payCheck = payDay.getPayCheck( empId );
        assertNotNull( payCheck );
        assertEquals( LocalDate.of( 2020, Month.JUNE, 30 ), payCheck.getPayDate() );
        assertEquals( 1000.0, payCheck.getAmount() );
        assertEquals( "Printed", payCheck.getDetails() );
        assertEquals( empId, payCheck.getEmployee().getId() );
    }

    @Test void testPayingSingleSalariedEmployeeOnWrongDate() {
        LocalDate june29Year2020 = LocalDate.of( 2020, Month.JUNE, 29 );
        PayDayTransaction payDay = new PayDayTransaction( june29Year2020 );
        payDay.execute();

        PayCheck payCheck = payDay.getPayCheck( empId );
        assertNull( payCheck );
    }

    @Test
    void testPaySingleHourlyEmployeeNoTimeCards() {
        LocalDate payDate = LocalDate.of( 2020, Month.JUNE, 13 ); // Saturday
        PayDayTransaction payDay = new PayDayTransaction( payDate );
        payDay.execute();

        validateHourlyPaycheck( payDay, empId2, payDate, 0.0 );
    }

    @Test
    void testPaySingleHourlyEmployeeOneTimeCard() {
        LocalDate payDate = LocalDate.of( 2020, Month.JUNE, 13 ); // Saturday
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );

        Transaction addTimeCard = new AddTimeCardTransaction( empId2, payDate, threeHoursAndThirtyMinutes );
        PayDayTransaction payDay = new PayDayTransaction( payDate );

        addTimeCard.execute();
        payDay.execute();

        validateHourlyPaycheck( payDay, empId2, payDate, 28.0 );
    }

    @Test
    void testPaySingleHourlyEmployeeOverTimeOneTimeCard() {
        LocalDate payDate = LocalDate.of( 2020, Month.JUNE, 13 ); // Saturday
        LocalTime nineHours = LocalTime.of( 9, 0 );

        Transaction addTimeCard = new AddTimeCardTransaction( empId2, payDate, nineHours );
        PayDayTransaction payDay = new PayDayTransaction( payDate );

        addTimeCard.execute();
        payDay.execute();

        // 9 hours of work means 8 hours are paid normally while the extra hour is paid 1.5 times the normal hour
        validateHourlyPaycheck( payDay, empId2, payDate, ( 8.0 + 1.5 ) * 8.0 );
    }

    @Test
    void testPaySingleHourlyEmployeeOnWrongDate() {
        LocalDate payDate = LocalDate.of( 2020, Month.JUNE, 12 ); // Friday
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );

        Transaction addTimeCard = new AddTimeCardTransaction( empId2, payDate, threeHoursAndThirtyMinutes );
        PayDayTransaction payDay = new PayDayTransaction( payDate );

        addTimeCard.execute();
        payDay.execute();

        PayCheck payCheck = payDay.getPayCheck( empId2 );
        assertNull( payCheck );
    }

    @Test
    void testPaySingleHourlyEmployeeTwoTimeCard() {
        LocalDate beforePayDate = LocalDate.of( 2020, Month.JUNE, 12 ); // Friday
        LocalDate payDate = LocalDate.of( 2020, Month.JUNE, 13 ); // Saturday
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );
        LocalTime fourHours = LocalTime.of( 4, 0 );

        Transaction addTimeCard = new AddTimeCardTransaction( empId2, beforePayDate, threeHoursAndThirtyMinutes );
        Transaction addTimeCard2 = new AddTimeCardTransaction( empId2, payDate, fourHours );
        PayDayTransaction payDay = new PayDayTransaction( payDate );

        addTimeCard.execute();
        addTimeCard2.execute();
        payDay.execute();

        validateHourlyPaycheck( payDay, empId2, payDate, 60.0 );
    }

    @Test
    void testPaySingleHourlyEmployeeWithTimeCardsSpanningThreePayPeriods() {
        LocalDate afterPayDate = LocalDate.of( 2020, Month.JUNE, 14 ); // Sunday
        LocalDate payDate = LocalDate.of( 2020, Month.JUNE, 13 ); // Saturday
        LocalDate previousPayDate = LocalDate.of( 2020, Month.JUNE, 6 ); // Previous Saturday
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );
        LocalTime fourHours = LocalTime.of( 4, 0 );

        Transaction addTimeCard = new AddTimeCardTransaction( empId2, payDate, threeHoursAndThirtyMinutes ); // the only valid one
        Transaction addTimeCard2 = new AddTimeCardTransaction( empId2, afterPayDate, fourHours );
        Transaction addTimeCard3 = new AddTimeCardTransaction( empId2, previousPayDate, fourHours );
        PayDayTransaction payDay = new PayDayTransaction( payDate );

        addTimeCard.execute();
        addTimeCard2.execute();
        addTimeCard3.execute();
        payDay.execute();

        validateHourlyPaycheck( payDay, empId2, payDate, 28.0 );
    }

    private void validateHourlyPaycheck( PayDayTransaction payDay, Long empId, LocalDate payDate, double expectedAmount ) {
        PayCheck payCheck = payDay.getPayCheck( empId );
        assertNotNull( payCheck );
        assertEquals( payDate, payCheck.getPayDate() );
        assertEquals( expectedAmount, payCheck.getAmount() );
        assertEquals( "Printed", payCheck.getDetails() );
    }
}