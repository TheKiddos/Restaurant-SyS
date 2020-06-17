package org.thekiddos.manager.repositories;

import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.models.Employee;
import org.thekiddos.manager.models.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static Map<Long, Employee> employees = new HashMap<>();
    private static Map<Long, Customer> customers = new HashMap<>();
    private static Map<Long, Table> tables = new HashMap<>();

    public static Employee getEmployeeById( Long employeeId ) {
        return employees.get( employeeId );
    }

    public static void addEmployee( Employee employee ) {
        employees.put( employee.getId(), employee );
    }

    public static void removeEmployeeById( Long empId ) {
        employees.remove( empId );
    }

    public static List<Employee> getEmployees() {
        return new ArrayList<>( employees.values() );
    }

    public static void addCustomer( Customer customer ) {
        customers.put( customer.getId(), customer );
    }

    public static Customer getCustomerById( Long customerId ) {
        return customers.get( customerId );
    }

    public static void removeCustomerById( Long customerId ) {
        customers.remove( customerId );
    }

    public static void addTable( Table table ) {
        tables.put( table.getId(), table );
    }

    public static Table getTableById( Long tableId ) {
        return tables.get( tableId );
    }

    public static void removeTableById( Long tableId ) {
        tables.remove( tableId );
    }
}
