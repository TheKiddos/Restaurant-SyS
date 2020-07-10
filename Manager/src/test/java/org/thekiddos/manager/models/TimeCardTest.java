package org.thekiddos.manager.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.payroll.models.Employee;
import org.thekiddos.manager.payroll.models.HourlyClassification;
import org.thekiddos.manager.payroll.models.TimeCard;
import org.thekiddos.manager.payroll.transactions.AddHourlyEmployeeTransaction;
import org.thekiddos.manager.payroll.transactions.AddSalariedEmployeeTransaction;
import org.thekiddos.manager.payroll.transactions.AddTimeCardTransaction;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class TimeCardTest {

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
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

    @Test
    void testAddTimeCardToEmployeeThatDoesNotExists() {
        assertThrows( IllegalArgumentException.class, () -> new AddTimeCardTransaction( 1L, LocalDate.now(), LocalTime.now() ) );
    }

    @Test
    void testAddTimeCardToNonHourlyEmployee() {
        Long empId = 1L;
        new AddSalariedEmployeeTransaction( empId, "asda", 10.0 ).execute();
        assertThrows( IllegalArgumentException.class, () -> new AddTimeCardTransaction( empId, LocalDate.now(), LocalTime.now() ) );
    }
}