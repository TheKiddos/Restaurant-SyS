package org.thekiddos.manager.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.api.mapper.TypeMapper;
import org.thekiddos.manager.api.model.ItemDTO;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.models.Type;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddCustomerTransaction;
import org.thekiddos.manager.transactions.AddItemTransaction;
import org.thekiddos.manager.transactions.AddTableTransaction;
import org.thekiddos.manager.transactions.ImmediateReservationTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class ItemServiceTest {
    private static final Long ITEM_ID = 1L, ITEM_ID_2 = 2L, TABLE_ID = 1L, TABLE_ID_2 = 2L, CUSTOMER_ID = 1L;
    private final ItemService itemService;
    private final TypeMapper typeMapper;

    @Autowired
    public ItemServiceTest( ItemService itemService, TypeMapper typeMapper ) {
        this.itemService = itemService;
        this.typeMapper = typeMapper;
    }

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
        fillDatabase();
    }

    void fillDatabase() {
        String imagePath = "https://cms.splendidtable.org/sites/default/files/styles/w2000/public/french-fries.jpg?itok=FS-YwUYH";
        AddItemTransaction addItem = new AddItemTransaction( ITEM_ID, "French Fries", 10.0 );
        addItem.withDescription( "Well it's French Fries what else to say!" )
                .withType( Type.type( "FOOD" ) )
                .withType( Type.type( "STARTER" ) )
                .withType( Type.type( "HOT" ) )
                .withType( Type.type( "SNACK" ) )
                .withCalories( 10000.0 )
                .withFat( 51.0 )
                .withProtein( 0.4 )
                .withCarbohydrates( 0.2 )
                .withImage( imagePath );
        addItem.execute();

        imagePath = "Yah Yah Yah";
        addItem = new AddItemTransaction( ITEM_ID_2, "Batata", 100.0 );
        addItem.withDescription( "Batataaaaaa" )
                .withType( Type.type( "COLD" ) )
                .withType( Type.type( "MILK" ) )
                .withType( Type.type( "SNACK" ) )
                .withCalories( 100.0 )
                .withFat( 5.0 )
                .withProtein( 1.0 )
                .withCarbohydrates( 1.0 )
                .withImage( imagePath );
        addItem.execute();

        new AddTableTransaction( TABLE_ID ).execute();
        new AddTableTransaction( TABLE_ID_2 ).execute();
        new AddCustomerTransaction( CUSTOMER_ID, "Kiddo", "mp4-12cs5@outlook.com", "12345678" ).execute();
        new ImmediateReservationTransaction( TABLE_ID, CUSTOMER_ID ).execute();
    }

    @Test
    void testGetItems() {
        List<ItemDTO> items = itemService.getItems();

        assertEquals( 2, items.size() );

        for ( ItemDTO itemDTO : items )
            validateItemDTO( itemDTO );
    }

    private void validateItemDTO( ItemDTO itemDTO ) {
        Item item = Database.getItemById( itemDTO.getId() );
        assertNotNull( item );
        assertEquals( item.getName(), itemDTO.getName() );
        assertEquals( item.getPrice(), itemDTO.getPrice() );
        assertEquals( item.getDescription(), itemDTO.getDescription() );
        assertEquals( item.getImagePath(), itemDTO.getImagePath() );
        assertEquals( item.getCalories(), itemDTO.getCalories() );
        assertEquals( item.getFat(), itemDTO.getFat() );
        assertEquals( item.getProtein(), itemDTO.getProtein() );
        assertEquals( item.getCarbohydrates(), itemDTO.getCarbohydrates() );
        assertEquals( item.getTypes().stream().map( typeMapper::typeToTypeDTO ).collect( Collectors.toSet() ), itemDTO.getTypes() );
    }

    @Test
    void testAllItemsInMenu() {
        assertTrue( itemService.allItemsInMenu( new ArrayList<>( Database.getItems() ) ) );

        Item item = new Item(
                Util.INVALID_ID,
                "test",
                10.0,
                1.0,
                1.0,
                1.0,
                1.0,
                "Hi",
                "HI",
                Set.copyOf( Database.getTypes() )
        );
        assertFalse( itemService.allItemsInMenu( List.of( item, Database.getItemById( ITEM_ID ), Database.getItemById( ITEM_ID_2 ) ) ) );
    }

    @Test
    void testAddItemsToOrder() {
        List<Item> orderedItems = new ArrayList<>( Database.getItems() );

        itemService.addItemsToOrder( TABLE_ID, orderedItems );
        Reservation reservation = Database.getCurrentReservationByTableId( TABLE_ID );
        assertEquals( 2, reservation.getOrder().getItemsQuantities().size() );
        assertEquals( 1, reservation.getOrder().getItemsQuantities().get( Database.getItemById( ITEM_ID ) ) );
        assertEquals( 1, reservation.getOrder().getItemsQuantities().get( Database.getItemById( ITEM_ID_2 ) ) );
    }

    @Test
    void testAddOrderItemsWithInActiveTable() {
        List<Item> orderedItems = new ArrayList<>( Database.getItems() );

        IllegalArgumentException exception = assertThrows( IllegalArgumentException.class, () -> itemService.addItemsToOrder( TABLE_ID_2, orderedItems ) );
        assertEquals( "No Current Reservations for the selected table", exception.getMessage() );
    }

    @Test
    void testAddOrderItemsWithItemsThatDoesNotExists() {
        List<Item> orderedItems = new ArrayList<>( Database.getItems() );
        orderedItems.add( new Item( Util.INVALID_ID, "Fake Item", 1000.0 ) );

        IllegalArgumentException exception = assertThrows( IllegalArgumentException.class, () -> itemService.addItemsToOrder( TABLE_ID, orderedItems ) );
        assertEquals( "One or more of the provided items are not served by the restaurant.", exception.getMessage() );
    }
}