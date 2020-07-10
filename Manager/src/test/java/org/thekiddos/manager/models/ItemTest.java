package org.thekiddos.manager.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class ItemTest {

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
    }

    @Test
    void testAddItemTransaction() {
        Long itemId = 14L;
        String imagePath = "https://cms.splendidtable.org/sites/default/files/styles/w2000/public/french-fries.jpg?itok=FS-YwUYH";
        AddItemTransaction addItem = new AddItemTransaction( itemId, "French Fries", 10.0 );
        addItem.withDescription( "Well it's French Fries what else to say!" )
                .withType( Type.FOOD )
                .withType( Type.STARTER )
                .withType( Type.HOT )
                .withType( Type.SNACK )
                .withCalories( 10000.0 )
                .withFat( 51.0 )
                .withProtein( 0.4 )
                .withCarbohydrates( 0.2 )
                .withImage( imagePath );
        addItem.execute();

        Item frenchFries = Database.getItemById( itemId );
        assertNotNull( frenchFries );
        assertEquals( "French Fries", frenchFries.getName() );
        assertEquals( 10.0, frenchFries.getPrice() );
        assertEquals( 10000.0, frenchFries.getCalories() );
        assertEquals( 51.0, frenchFries.getFat() );
        assertEquals( 0.4, frenchFries.getProtein() );
        assertEquals( 0.2, frenchFries.getCarbohydrates() );
        assertEquals( imagePath, frenchFries.getImagePath() );
        assertEquals( "Well it's French Fries what else to say!", frenchFries.getDescription() );
        Set<Type> types = new HashSet<>();
        types.add( Type.FOOD );
        types.add( Type.STARTER );
        types.add( Type.HOT );
        types.add( Type.SNACK );
        assertEquals( types, frenchFries.getTypes() );
    }

    @Test
    void testAddItemWithSameId() {
        Long itemId = 1L;
        AddItemTransaction addItem = new AddItemTransaction( itemId, "French Fries", 10.0 );
        addItem.execute();

        assertThrows( IllegalArgumentException.class, () -> new AddItemTransaction( itemId, "French Fries", 10.0 ) );
    }

    @Test
    void testDeleteItemTransaction() {
        Long itemId = 14L;
        new AddItemTransaction( itemId, "French Fries", 10.0 ).execute();

        Transaction deleteItem = new DeleteItemTransaction( itemId );
        deleteItem.execute();

        Item frenchFries = Database.getItemById( itemId );
        assertNull( frenchFries );
    }

    @Test
    void testDeleteItemThatDoesNotExists() {
        assertThrows( IllegalArgumentException.class, () -> new DeleteItemTransaction( 1L ) );
    }

    @Test
    void testDeleteOrderedItem() {
        Long tableId = 1L, customerId = 1L, itemId = 1L;
        new AddTableTransaction( tableId ).execute();
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();
        new AddItemTransaction( itemId, "French Fries", 10.0 ).execute();
        new ImmediateReservationTransaction( tableId, customerId ).execute();

        AddItemsToServiceTransaction service = new AddItemsToReservationTransaction( tableId );
        service.addItem( itemId );
        service.execute();

        assertThrows( IllegalArgumentException.class, () -> new DeleteItemTransaction( itemId ) );
    }
}