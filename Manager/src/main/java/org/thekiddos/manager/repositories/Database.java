package org.thekiddos.manager.repositories;

import org.springframework.context.ApplicationContext;
import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.models.Table;
import org.thekiddos.manager.payroll.models.Employee;

import java.time.LocalDate;
import java.util.*;

public class Database {
    private static final Map<Long, Employee> employees = new HashMap<>();
    private static final Map<Long, List<Reservation>> reservations = new HashMap<>();

    private static ApplicationContext applicationContext;

    private static TableRepository tableRepository;
    private static CustomerRepository customerRepository;
    private static ItemRepository itemRepository;
    // TODO rename getAll methods to include id in the name
    // TODO use the Optional later
    // TODO Maybe getIds is bad for performance

    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    public static void setUpDatabase( ApplicationContext applicationContext ) {
        Database.applicationContext = applicationContext;
        tableRepository = getBean( TableRepository.class );
        customerRepository = getBean( CustomerRepository.class );
        itemRepository = getBean( ItemRepository.class );
    }

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
        List<Reservation> tableReservations = reservations.get( reservation.getTableId() );
        if ( tableReservations == null )
            tableReservations = new ArrayList<>();

        tableReservations.add( reservation );

        reservations.put( reservation.getTableId(), tableReservations );
    }

    public static List<Reservation> getReservationsByTableId( Long tableId ) {
        return reservations.computeIfAbsent( tableId, k -> new ArrayList<>() );
    }

    public static List<Reservation> getReservationsByCustomerId( Long customerId ) {
        List<Reservation> customerReservations = new ArrayList<>();
        for ( List<Reservation> tableReservations : reservations.values() )
            for ( Reservation reservation : tableReservations )
                if ( reservation.getCustomerId().equals( customerId ) )
                    customerReservations.add( reservation );
        return customerReservations;
    }

    public static void deleteReservation( Long tableId, LocalDate reservationDate ) {
        List<Reservation> tableReservation = Database.getReservationsByTableId( tableId );
        for ( int i = 0; i < tableReservation.size(); ++i )
            if ( tableReservation.get( i ).getDate().equals( reservationDate ) ) {
                tableReservation.remove( i );
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

    public static void init() {
        tableRepository.deleteAll();
        customerRepository.deleteAll();
        itemRepository.deleteAll();
        reservations.clear();
        employees.clear();
    }

    public static Set<Long> getFreeTablesOn( LocalDate date ) {
        Set<Long> freeTablesOnDate = getTablesId();
        Set<Long> reservedTables = new HashSet<>();
        for ( List<Reservation> tableReservations : reservations.values() ) {
            tableReservations.stream().filter( reservation -> reservation.getDate().equals( date ) )
                    .forEach( reservation -> reservedTables.add( reservation.getTableId() ) );
        }

        freeTablesOnDate.removeAll( reservedTables );
        return freeTablesOnDate;
    }

    public static Set<Long> getTablesId() {
        HashSet<Long> ids = new HashSet<>();

        tableRepository.findAll().forEach( sittingTable -> ids.add( sittingTable.getId() ) );

        return ids;
    }

    public static Set<Long> getCustomersId() {
        HashSet<Long> ids = new HashSet<>();

        customerRepository.findAll().forEach( customer -> ids.add( customer.getId() ) );

        return ids;
    }

    public static List<Reservation> getCurrentReservations() {
        List<Reservation> currentReservations = new ArrayList<>();
        for ( Long tableId : getTablesId() ) {
            Reservation reservation = getCurrentReservationByTableId( tableId );
            if ( reservation != null )
                currentReservations.add( reservation );
        }
        return currentReservations;
    }

    public static Set<Item> getItems() {
        HashSet<Item> items = new HashSet<>();

        itemRepository.findAll().forEach( items::add );

        return items;
    }

    public static Set<Long> getEmployeesId() {
        return new HashSet<>( employees.keySet() );
    }

    // TODO protect against nulls in all Transaction/Models
}
