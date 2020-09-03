package org.thekiddos.manager.repositories;

import org.springframework.context.ApplicationContext;
import org.thekiddos.manager.StartUpAction;
import org.thekiddos.manager.models.*;
import org.thekiddos.manager.payroll.models.Employee;
import org.thekiddos.manager.payroll.models.HourlyClassification;
import org.thekiddos.manager.payroll.models.PaymentClassification;
import org.thekiddos.manager.payroll.models.TimeCard;
import org.thekiddos.manager.payroll.repositories.EmployeeRepository;
import org.thekiddos.manager.payroll.repositories.TimeCardRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A Facade to the repositories
 */
public final class Database {
    private static ApplicationContext applicationContext;

    private static TableRepository tableRepository;
    private static CustomerRepository customerRepository;
    private static ItemRepository itemRepository;
    private static ReservationsRepository reservationsRepository;
    private static TimeCardRepository timeCardRepository;
    private static EmployeeRepository employeeRepository;
    private static TypeRepository typeRepository;
    private static TelegramUserRepository telegramUserRepository;
    private static DeliveryRepository deliveryRepository;
    private static MessageRepository messageRepository;

    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    /**
     * This method should be called as soon as the context loads (and that is done by {@link StartUpAction#setUpDatabase()}
     * it's main role is to get all the Repositories so they can be accessed from this class
     * @param applicationContext Spring Boot Context
     */
    public static void setUpDatabase( ApplicationContext applicationContext ) {
        Database.applicationContext = applicationContext;
        tableRepository = getBean( TableRepository.class );
        customerRepository = getBean( CustomerRepository.class );
        itemRepository = getBean( ItemRepository.class );
        reservationsRepository = getBean( ReservationsRepository.class );
        timeCardRepository = getBean( TimeCardRepository.class );
        employeeRepository = getBean( EmployeeRepository.class );
        typeRepository = getBean( TypeRepository.class );
        telegramUserRepository = getBean( TelegramUserRepository.class );
        deliveryRepository = getBean( DeliveryRepository.class );
        messageRepository = getBean( MessageRepository.class );
    }

    /**
     * Deletes everything from the database except for the types
     */
    public static void deleteAll() {
        reservationsRepository.deleteAll();
        deliveryRepository.deleteAll();
        tableRepository.deleteAll();
        customerRepository.deleteAll();
        itemRepository.deleteAll();
        timeCardRepository.deleteAll();
        employeeRepository.deleteAll();
        telegramUserRepository.deleteAll();
    }

    public static void deleteTypes() {
        typeRepository.deleteAll();
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
        List<Reservation> activeReservations = reservationsRepository.findAllByActiveTrue();
        List<Reservation> currentReservation = reservationsRepository.findByServiceIdDate( LocalDate.now() );

        currentReservation.addAll( activeReservations );

        // TODO make sure this is right
        return currentReservation.stream().distinct().collect( Collectors.toList() );
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

    public static void deleteReservation( Reservation reservation ) {
        reservationsRepository.delete( reservation );
    }

    public static boolean customerHasReservations( Long customerId ) {
        return getReservationsByCustomerId( customerId ).size() > 0;
    }

    public static void removeCustomer( Customer customer ) {
        customerRepository.delete( customer );
    }

    public static void removeItem( Item item ) {
        itemRepository.delete( item );
    }

    public static boolean isItemInAnyOrder( Item item ) {
        List<Reservation> currentReservations = getCurrentReservations();

        for ( Reservation reservation : currentReservations ) {
            if ( reservation.getOrder().containsItem( item ) )
                return true;
        }

        return false;
    }

    public static boolean tableHasReservations( Long id ) {
        return getReservationsByTableId( id ).size() > 0;
    }

    public static Reservation getTableReservationOnDate( Long id, LocalDate on ) {
        List<Reservation> tableReservations = Database.getReservationsByTableId( id );

        if ( tableReservations.size() == 0 )
            return null;

        for ( Reservation reservation : tableReservations ) {
            if ( reservation.getDate().equals( on ) )
                return reservation;
        }

        return null;
    }

    public static void removeTable( Table table ) {
        tableRepository.delete( table );
    }

    public static void addTimeCard( TimeCard timeCard ) {
        timeCardRepository.save( timeCard );
    }

    public static Customer getCustomerByEmail( String email ) {
        return customerRepository.findByEmail( email ).orElse( null );
    }

    public static Type getTypeId( String typeName ) {
        return typeRepository.findById( typeName ).orElse( null );
    }

    public static void addType( Type type ) {
        typeRepository.save( type );
    }

    public static Collection<Type> getTypes() {
        ArrayList<Type> types = new ArrayList<>();
        typeRepository.findAll().forEach( types::add );
        return types;
    }

    public static TelegramUser getTelegramUser( Integer userID ) {
        TelegramUser telegramUser = new TelegramUser( userID );
        Optional<TelegramUser> fromDatabase = telegramUserRepository.findById( userID );
        if ( fromDatabase.isPresent() )
            return fromDatabase.get();

        telegramUserRepository.save( telegramUser );
        return telegramUser;
    }

    public static void updateTelegramUser( TelegramUser currentTelegramUser ) {
        telegramUserRepository.save( currentTelegramUser );
    }

    public static Set<Item> getRecommendationsFor( String email ) {
        Customer customer = getCustomerByEmail( email );
        return customer.getRecommendations();
    }

    public static List<Delivery> getDeliveryByCustomerId( Long customerId ) {
        return deliveryRepository.findByServiceIdCustomerId( customerId );
    }

    public static Delivery getDeliveryById( Long customerId, LocalDate data ) {
        return deliveryRepository.findById( new ServiceId( customerId, data ) ).orElse( null );
    }

    public static void addDelivery( Delivery delivery ) {
        deliveryRepository.save( delivery );
    }

    public static void deleteDelivery( Delivery delivery ) {
        deliveryRepository.delete( delivery );
    }

    public static List<Delivery> getDeliveries() {
        List<Delivery> deliveries = new ArrayList<>( );
        deliveryRepository.findAll().forEach( deliveries::add );
        return deliveries;
    }

    public static List<Reservation> getReservations() {
        List<Reservation> reservations = new ArrayList<>();
        reservationsRepository.findAll().forEach( reservations::add );
        return reservations;
    }

    public static void deleteMessages() {
        messageRepository.deleteAll();
    }

    public static void addMessage( Message message ) {
        messageRepository.save( message );
    }

    public static List<Message> getMessages() {
        ArrayList<Message> messages = new ArrayList<>();
        messageRepository.findAll().forEach( messages::add );
        return messages;
    }

    // TODO
    // if there is time add tests
    // FIX date in laravel not zone specific use Carbon
}
