package org.thekiddos.manager.repositories;

import org.thekiddos.manager.models.Employee;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Map<Long, Employee> employees = new HashMap<>();

    public static Employee getEmployeeById( Long employeeId ) {
        return employees.get( employeeId );
    }

    public static void addEmployee( Employee employee ) {
        employees.put( employee.getId(), employee );
    }

    public static void removeEmployeeById( Long empId ) {
        employees.remove( empId );
    }
}
