package org.thekiddos.manager;

import org.junit.jupiter.api.Test;
import org.thekiddos.manager.models.Employee;
import org.thekiddos.manager.repositories.Database;
import static org.junit.jupiter.api.Assertions.*;

class PayrollTest {
    @Test
    void testAddSalariedEmployee() {
        Long empId = 1L;
        Transaction addSalariedEmployee = new AddSalariedEmployee( empId, "Zahlt", 1000.0 );
        addSalariedEmployee.execute();

        Employee emp = Database.getEmployeeById( empId );
        assertEquals( "Zahlt", emp.getName() );

        PaymentClassification paymentClassification = emp.getPaymentClassification();
        SalariedClassification salariedClassification = (SalariedClassification)paymentClassification;
        assertNotNull( paymentClassification );
        assertEquals( 1000.0, salariedClassification.getSalary() );

        PaymentSchedule paymentSchedule = emp.getPaymentSchedule();
        MonthlySchedule monthlySchedule = (MonthlySchedule)paymentSchedule;
        assertNotNull( monthlySchedule );

        PaymentMethod paymentMethod = emp.getPaymentMethod();
        HoldMethod holdMethod = (HoldMethod)paymentMethod;
        assertNotNull( holdMethod );
    }
}