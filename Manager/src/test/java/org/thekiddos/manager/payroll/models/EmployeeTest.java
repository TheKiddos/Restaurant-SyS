package org.thekiddos.manager.payroll.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.payroll.transactions.*;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.Transaction;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class EmployeeTest {

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
    }

    @Test
    void testAddSalariedEmployee() {
        Long empId = 1L;
        AddSalariedEmployeeTransaction addSalariedEmployee = new AddSalariedEmployeeTransaction( empId, "Zahlt", 1000.0 );
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
        AddHourlyEmployeeTransaction addHourlyEmployee = new AddHourlyEmployeeTransaction( empId, "Zahlt", 8.0 );
        addHourlyEmployee.setOverHoursBonusRate( addHourlyEmployee.getOverHoursBonusRate() + 1 );
        addHourlyEmployee.setOverHoursThreshold( addHourlyEmployee.getOverHoursThreshold() + 1 );
        addHourlyEmployee.setHourlyRate( addHourlyEmployee.getHourlyRate() + 1 );
        addHourlyEmployee.execute();

        Employee emp = Database.getEmployeeById( empId );
        assertEquals( "Zahlt", emp.getName() );

        PaymentClassification paymentClassification = emp.getPaymentClassification();
        HourlyClassification hourlyClassification = (HourlyClassification)paymentClassification;
        assertNotNull( paymentClassification );
        assertEquals( 9.0, hourlyClassification.getHourlyRate() );
        assertEquals( 2.5, hourlyClassification.getOverHoursBonusRate() );
        assertEquals( 9.0, hourlyClassification.getOverHoursThreshold() );

        PaymentSchedule paymentSchedule = emp.getPaymentSchedule();
        WeeklySchedule weeklySchedule = (WeeklySchedule)paymentSchedule;
        assertNotNull( weeklySchedule );

        PaymentMethod paymentMethod = emp.getPaymentMethod();
        HoldMethod holdMethod = (HoldMethod)paymentMethod;
        assertNotNull( holdMethod );
    }

    @Test
    void testAddEmployeeWithSameId() {
        Long empId = 1L;
        new AddSalariedEmployeeTransaction( empId, "Zahlt", 1000.0 ).execute();
        Long empId2 = 2L;
        new AddHourlyEmployeeTransaction( empId2, "Zahlt", 1000.0 ).execute();

        assertThrows( IllegalArgumentException.class, () -> new AddSalariedEmployeeTransaction( empId, "Zahlt", 1000.0 ) );
        assertThrows( IllegalArgumentException.class, () -> new AddHourlyEmployeeTransaction( empId, "Zahlt", 10.0 ) );

        assertThrows( IllegalArgumentException.class, () -> new AddSalariedEmployeeTransaction( empId2, "Zahlt", 1000.0 ) );
        assertThrows( IllegalArgumentException.class, () -> new AddHourlyEmployeeTransaction( empId2, "Zahlt", 10.0 ) );
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
    void testDeleteEmployeeThatDoesNotExists() {
        assertThrows( IllegalArgumentException.class, () -> new DeleteEmployeeTransaction( 1L ) );
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

        ChangeToHourlyTransaction changeToHourly = new ChangeToHourlyTransaction( empId, 8.0 );
        changeToHourly.setOverHoursBonusRate( changeToHourly.getOverHoursBonusRate() + 1 );
        changeToHourly.setOverHoursThreshold( changeToHourly.getOverHoursThreshold() + 1 );
        changeToHourly.setHourlyRate( changeToHourly.getHourlyRate() + 1 );
        changeToHourly.execute();

        Employee emp = Database.getEmployeeById( empId );

        PaymentClassification paymentClassification = emp.getPaymentClassification();
        HourlyClassification hourlyClassification = (HourlyClassification)paymentClassification;
        assertNotNull( paymentClassification );
        assertEquals( 9.0, hourlyClassification.getHourlyRate() );
        assertEquals( 2.5, hourlyClassification.getOverHoursBonusRate() );
        assertEquals( 9.0, hourlyClassification.getOverHoursThreshold() );

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
    void testChangeSalariedToSick() {
        Long empId = 1L;
        Transaction addEmployee = new AddSalariedEmployeeTransaction( empId, "Zahlt", 1000.0 );
        addEmployee.execute();

        ChangeClassificationTransaction changeToSick = new ChangeToSickTransaction( empId );
        changeToSick.execute();

        Employee employee = Database.getEmployeeById( empId );

        assertNotNull( employee );
        assertEquals( "Zahlt", employee.getName() );

        PaymentClassification paymentClassification = employee.getPaymentClassification();
        SickClassification sickClassification = (SickClassification)paymentClassification;
        assertNotNull( sickClassification );
        assertEquals( 1000.0, sickClassification.getCompensation() );

        PaymentSchedule paymentSchedule = employee.getPaymentSchedule();
        MonthlySchedule monthlySchedule = (MonthlySchedule)paymentSchedule;
        assertNotNull( monthlySchedule );
    }
}