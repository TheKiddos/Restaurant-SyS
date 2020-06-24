package org.thekiddos.manager.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.thekiddos.manager.AddHourlyEmployeeTransaction;
import org.thekiddos.manager.AddTimeCardTransaction;
import org.thekiddos.manager.Transaction;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TimeCardTest {

    @BeforeEach
    void setUpDatabase() {
        Database.init();
    }

    @Test
    void testAddTimeCardTransaction() {
        Long empId = 1L;
        Transaction addEmployee = new AddHourlyEmployeeTransaction( empId, "Zahlt", 8.0 );
        addEmployee.execute();

        LocalDate fifthOfNovemberNextYear = LocalDate.of( LocalDate.now().plusYears( 1 ).getYear(), Month.NOVEMBER, 5 );
        LocalTime threeHoursAndThirtyMinutes = LocalTime.of( 3, 30 );
        Transaction addTimeCard = new AddTimeCardTransaction( empId, fifthOfNovemberNextYear, threeHoursAndThirtyMinutes );
        addTimeCard.execute();

        Employee emp = Database.getEmployeeById( empId );
        assertNotNull( emp );

        HourlyClassification hourlyClassification = (HourlyClassification)emp.getPaymentClassification();
        assertNotNull( hourlyClassification );

        TimeCard timeCard = hourlyClassification.getTimeCard( fifthOfNovemberNextYear );
        assertNotNull( timeCard );

        assertEquals( threeHoursAndThirtyMinutes, timeCard.getTimeWorked() );
    }
}