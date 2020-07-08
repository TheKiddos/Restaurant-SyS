package org.thekiddos.manager.repositories;

import org.springframework.context.ApplicationContext;
import org.thekiddos.manager.models.*;
import org.thekiddos.manager.payroll.models.Employee;
import org.thekiddos.manager.payroll.models.HourlyClassification;
import org.thekiddos.manager.payroll.models.PaymentClassification;
import org.thekiddos.manager.payroll.models.TimeCard;
import org.thekiddos.manager.payroll.repositories.EmployeeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Database {
    private static ApplicationContext applicationContext;

    private static TableRepository tableRepository;
    private static CustomerRepository customerRepository;
    private static ItemRepository itemRepository;
    private static ReservationsRepository reservationsRepository;
    private static TimeCardRepository timeCardRepository;
    private static EmployeeRepository employeeRepository;
    // TODO rename getAll methods to include id in the name
    // TODO use the Optional later
    // TODO Maybe getIds is bad for performance
    // TODO Resolve when stuff are allowed to be deleted

    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    public static void setUpDatabase( ApplicationContext applicationContext ) {
        Database.applicationContext = applicationContext;
        tableRepository = getBean( TableRepository.class );
        customerRepository = getBean( CustomerRepository.class );
        itemRepository = getBean( ItemRepository.class );
        reservationsRepository = getBean( ReservationsRepository.class );
        timeCardRepository = getBean( TimeCardRepository.class );
        employeeRepository = getBean( EmployeeRepository.class );
    }

    public static void deleteAll() {
        reservationsRepository.deleteAll();
        tableRepository.deleteAll();
        customerRepository.deleteAll();
        itemRepository.deleteAll();
        timeCardRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    public static Employee getEmployeeById( Long employeeId ) {
        return employeeRepository.findById( employeeId ).orElse( null );
    }

    public static void addEmployee( Employee employee ) {
        employeeRepository.save( employee );
    }

    public static void removeEmployeeById( Long empId ) {
        Employee employee = getEmployeeById( empId );
        PaymentClassification paymentClassification = employee.getPaymentClassification();
        if ( paymentClassification instanceof HourlyClassification )
            deleteTimeCardsFor( (HourlyClassification) paymentClassification );
        employeeRepository.delete( employee );
    }

    private static void deleteTimeCardsFor( HourlyClassification hourlyClassification ) {
        Iterable<TimeCard> timeCards = timeCardRepository.findAll();
        timeCards.forEach( timeCard -> {
            if ( timeCard.getTimeCardId().getHourlyClassificationId().equals( hourlyClassification.getId() ))
                timeCardRepository.delete( timeCard );
        } );
    }

    public static List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();

        employeeRepository.findAll().forEach( employees::add );

        return employees;
    }

    public static void addCustomer( Customer customer ) {
        customerRepository.save( customer );
    }

    public static Customer getCustomerById( Long customerId ) {
        return customerRepository.findById( customerId ).orElse( null );
    }

    public static void removeCustomerById( Long customerId ) {
        customerRepository.deleteById( customerId );
    }

    public static void addTable( Table table ) {
        tableRepository.save( table );
    }

    public static Table getTableById( Long tableId ) {
        return tableRepository.findById( tableId ).orElse( null );
    }

    public static void removeTableById( Long tableId ) {
        tableRepository.deleteById( tableId );
    }

    public static void addReservation( Reservation reservation ) {
        reservationsRepository.save( reservation );
    }

    public static List<Reservation> getReservationsByTableId( Long tableId ) {
        return reservationsRepository.findByTableId( tableId );
    }

    public static List<Reservation> getReservationsByCustomerId( Long customerId ) {
        return reservationsRepository.findByServiceIdCustomerId( customerId );
    }

    public static void deleteReservation( Long tableId, LocalDate reservationDate ) {
        List<Reservation> tableReservation = Database.getReservationsByTableId( tableId );
        for ( Reservation reservation : tableReservation )
            if ( reservation.getDate().equals( reservationDate ) ) {
                reservationsRepository.delete( reservation );
                return;
            }
    }

    public static Set<Long> getItemsId() {
        HashSet<Long> ids = new HashSet<>();

        itemRepository.findAll().forEach( item -> ids.add( item.getId() ) );

        return ids;
    }

    public static Item getItemById( Long itemId ) {
        return itemRepository.findById( itemId ).orElse( null );
    }

    public static void addItem( Item item ) {
        itemRepository.save( item );
    }

    public static void removeItemById( Long itemId ) {
        itemRepository.deleteById( itemId );
    }

    public static Reservation getCurrentReservationByTableId( Long tableId ) {
        List<Reservation> tableReservation = Database.getReservationsByTableId( tableId );
        LocalDate now = LocalDate.now();
        for ( Reservation reservation : tableReservation ) {
            if ( reservation.getDate().equals( now ) )
                return reservation;
        }
        return null;
    }

    public static Set<Long> getFreeTablesOn( LocalDate date ) {
        Set<Long> freeTablesOnDate = getTablesId();
        Set<Long> reservedTables = new HashSet<>();

        reservationsRepository.findByServiceIdDate( date ).forEach( reservation -> reservedTables.add( reservation.getReservedTableId() ) );

        freeTablesOnDate.removeAll( reservedTables );
        return freeTablesOnDate;
    }

    public static Set<Long> getTablesId() {
        HashSet<Long> ids = new HashSet<>();

        tableRepository.findAll().forEach( table -> ids.add( table.getId() ) );

        return ids;
    }

    public static Set<Long> getCustomersId() {
        HashSet<Long> ids = new HashSet<>();

        customerRepository.findAll().forEach( customer -> ids.add( customer.getId() ) );

        return ids;
    }

    public static List<Reservation> getCurrentReservations() {
        List<Reservation> currentReservations = new ArrayList<>();

        reservationsRepository.findByServiceIdDate( LocalDate.now() ).forEach( currentReservations::add );

        return currentReservations;
    }

    public static Set<Item> getItems() {
        HashSet<Item> items = new HashSet<>();

        itemRepository.findAll().forEach( items::add );

        return items;
    }

    public static Set<Long> getEmployeesId() {
        Set<Long> employeesId = new HashSet<>( );

        employeeRepository.findAll().forEach( employee -> employeesId.add( employee.getId() ) );

        return employeesId;
    }

    public static Reservation getReservationById( Long customerId, LocalDate date ) {
        return reservationsRepository.findById( new ServiceId( customerId, date ) ).orElse( null );
    }

    // TODO protect against nulls in all Transaction/Models
}
