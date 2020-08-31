package org.thekiddos.manager.payroll.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.payroll.transactions.*;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.Transaction;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class PayCheckTest {
    private final Long salariedEmployeeId = 1L, hourlyEmployeeId = 2L, sickSalariedEmployeeId = 3L, sickHourlyEmployeeId = 4L;
    private final LocalDate saturdayJuneNextYear = LocalDate.of( LocalDate.now().plusYears( 1 ).getYear(), Month.JUNE, 1 ).with( TemporalAdjusters.next( DayOfWeek.SATURDAY ) );

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
        fillDatabase();
    }

    private void fillDatabase() {
        new AddSalariedEmployeeTransaction( salariedEmployeeId, "Zahlt", 1000.0 ).execute();
        new AddHourlyEmployeeTransaction( hourlyEmployeeId, "Zahlt", 8.0 ).execute();

        new AddSalariedEmployeeTransaction( sickSalariedEmployeeId, "Zahlt", 1000.0 ).execute();
        new ChangeToSickTransaction( sickSalariedEmployeeId ).execute();

        new AddHourlyEmployeeTransaction( sickHourlyEmployeeId, "Zahlt", 100.0 ).execute();
        new ChangeToSickTransaction( sickHourlyEmployeeId ).execute();
    }

    @Test
    void testPayOnWrongDay() {
        assertThrows( IllegalArgumentException.class, () -> new PayDayTransaction( LocalDate.now().minusDays( 1 ) ) );
    }

    @Test
    void testPaySingleSalariedEmployee() {
        LocalDate endOfJuneNextYear = LocalDate.of( LocalDate.now().plusYears( 1 ).getYear(), Month.JUNE, 30 );
        PayDayTransaction payDay = new PayDayTransaction( endOfJuneNextYear );
        payDay.execute();

        PayCheck payCheck = payDay.getPayCheck( salariedEmployeeId );
        assertNotNull( payCheck );
        assertEquals( endOfJuneNextYear, payCheck.getPayDate() );
        assertEquals( 1000.0, payCheck.getAmount() );
        assertEquals( "Printed", payCheck.getDetails() );
        assertEquals( salariedEmployeeId, payCheck.getEmployee().getId() );
    }

    @Test void testPayingSingleSalariedEmployeeOnWrongDate() {
        LocalDate june29NextYear = LocalDate.of( LocalDate.now().plusYears( 1 ).getYear(), Month.JUNE, 29 );
        PayDayTransaction payDay = new PayDayTransaction( june29NextYear );
        payDay.execute();

        PayCheck payCheck = payDay.getPayCheck( salariedEmployeeId );
        assertNull( payCheck );
    }

    @Test
    void testPaySingleHourlyEmployeeNoTimeCards() {
        PayDayTransaction payDay = new PayDayTransaction( saturdayJuneNextYear );
        payDay.execute();

        validateHourlyPaycheck( payDay, hourlyEmployeeId, saturdayJuneNextYear, 0.0 );
    }

    @Test
    void testPaySingleHourlyEmployeeOneTimeCard() {
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );

        Transaction addTimeCard = new AddTimeCardTransaction( hourlyEmployeeId, saturdayJuneNextYear, threeHoursAndThirtyMinutes );
        PayDayTransaction payDay = new PayDayTransaction( saturdayJuneNextYear );

        addTimeCard.execute();
        payDay.execute();

        validateHourlyPaycheck( payDay, hourlyEmployeeId, saturdayJuneNextYear, 28.0 );
    }

    @Test
    void testPaySingleHourlyEmployeeOverTimeOneTimeCard() {
        LocalTime nineHours = LocalTime.of( 9, 0 );

        Transaction addTimeCard = new AddTimeCardTransaction( hourlyEmployeeId, saturdayJuneNextYear, nineHours );
        PayDayTransaction payDay = new PayDayTransaction( saturdayJuneNextYear );

        addTimeCard.execute();
        payDay.execute();

        // 9 hours of work means 8 hours are paid normally while the extra hour is paid 1.5 times the normal hour
        validateHourlyPaycheck( payDay, hourlyEmployeeId, saturdayJuneNextYear, ( 8.0 + 1.5 ) * 8.0 );
    }

    @Test
    void testPaySingleHourlyEmployeeOnWrongDate() {
        LocalDate beforePayDate = saturdayJuneNextYear.minusDays( 1 );
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );

        Transaction addTimeCard = new AddTimeCardTransaction( hourlyEmployeeId, beforePayDate, threeHoursAndThirtyMinutes );
        PayDayTransaction payDay = new PayDayTransaction( beforePayDate );

        addTimeCard.execute();
        payDay.execute();

        PayCheck payCheck = payDay.getPayCheck( hourlyEmployeeId );
        assertNull( payCheck );
    }

    @Test
    void testPaySingleHourlyEmployeeTwoTimeCard() {
        LocalDate beforePayDate = saturdayJuneNextYear.minusDays( 1 );
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );
        LocalTime fourHours = LocalTime.of( 4, 0 );

        Transaction addTimeCard = new AddTimeCardTransaction( hourlyEmployeeId, beforePayDate, threeHoursAndThirtyMinutes );
        Transaction addTimeCard2 = new AddTimeCardTransaction( hourlyEmployeeId, saturdayJuneNextYear, fourHours );
        PayDayTransaction payDay = new PayDayTransaction( saturdayJuneNextYear );

        addTimeCard.execute();
        addTimeCard2.execute();
        payDay.execute();

        validateHourlyPaycheck( payDay, hourlyEmployeeId, saturdayJuneNextYear, 60.0 );
    }

    @Test
    void testPaySingleHourlyEmployeeWithTimeCardsSpanningThreePayPeriods() {
        LocalDate afterPayDate = saturdayJuneNextYear.plusDays( 1 );
        LocalDate previousPayDate = saturdayJuneNextYear.minusWeeks( 1 );
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );
        LocalTime fourHours = LocalTime.of( 4, 0 );

        Transaction addTimeCard = new AddTimeCardTransaction( hourlyEmployeeId, saturdayJuneNextYear, threeHoursAndThirtyMinutes ); // the only valid one
        Transaction addTimeCard2 = new AddTimeCardTransaction( hourlyEmployeeId, afterPayDate, fourHours );
        Transaction addTimeCard3 = new AddTimeCardTransaction( hourlyEmployeeId, previousPayDate, fourHours );
        PayDayTransaction payDay = new PayDayTransaction( saturdayJuneNextYear );

        addTimeCard.execute();
        addTimeCard2.execute();
        addTimeCard3.execute();
        payDay.execute();

        validateHourlyPaycheck( payDay, hourlyEmployeeId, saturdayJuneNextYear, 28.0 );
    }

    private void validateHourlyPaycheck( PayDayTransaction payDay, Long empId, LocalDate payDate, double expectedAmount ) {
        PayCheck payCheck = payDay.getPayCheck( empId );
        assertNotNull( payCheck );
        assertEquals( payDate, payCheck.getPayDate() );
        assertEquals( expectedAmount, payCheck.getAmount() );
        assertEquals( "Printed", payCheck.getDetails() );
    }

    @Test
    void testCompensateSickSalariedEmployee() {
        LocalDate endOfJuneNextYear = LocalDate.of( LocalDate.now().plusYears( 1 ).getYear(), Month.JUNE, 30 );
        PayDayTransaction payDay = new PayDayTransaction( endOfJuneNextYear );
        payDay.execute();

        PayCheck payCheck = payDay.getPayCheck( sickSalariedEmployeeId );
        assertNotNull( payCheck );
        assertEquals( endOfJuneNextYear, payCheck.getPayDate() );
        assertEquals( 1000.0, payCheck.getAmount() );
        assertEquals( "Printed", payCheck.getDetails() );
        assertEquals( sickSalariedEmployeeId, payCheck.getEmployee().getId() );
    }

    @Test
    void testCompensateSickHourlyEmployee() {
        LocalDate endOfJuneNextYear = LocalDate.of( LocalDate.now().plusYears( 1 ).getYear(), Month.JUNE, 30 );
        PayDayTransaction payDay = new PayDayTransaction( endOfJuneNextYear );
        payDay.execute();

        PayCheck payCheck = payDay.getPayCheck( sickHourlyEmployeeId );
        assertNotNull( payCheck );
        assertEquals( endOfJuneNextYear, payCheck.getPayDate() );
        assertEquals( 100.0 * 8 * 30, payCheck.getAmount() );
        assertEquals( "Printed", payCheck.getDetails() );
        assertEquals( sickHourlyEmployeeId, payCheck.getEmployee().getId() );
    }
}