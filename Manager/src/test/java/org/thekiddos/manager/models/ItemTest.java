package org.thekiddos.manager.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddItemTransaction;
import org.thekiddos.manager.transactions.DeleteItemTransaction;
import org.thekiddos.manager.transactions.Transaction;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class ItemTest {

    @BeforeEach
    void setUpDatabase() {
        Database.init();
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
    void testDeleteItemTransaction() {
        Long itemId = 14L;
        new AddItemTransaction( itemId, "French Fries", 10.0 ).execute();

        Transaction deleteItem = new DeleteItemTransaction( itemId );
        deleteItem.execute();

        Item frenchFries = Database.getItemById( itemId );
        assertNull( frenchFries );
    }

    // TODO test delete item in use now
}