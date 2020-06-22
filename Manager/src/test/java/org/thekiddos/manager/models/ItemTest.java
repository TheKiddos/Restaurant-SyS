package org.thekiddos.manager.models;

import org.junit.jupiter.api.Test;
import org.thekiddos.manager.AddItemTransaction;
import org.thekiddos.manager.DeleteItemTransaction;
import org.thekiddos.manager.Transaction;
import org.thekiddos.manager.repositories.Database;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    @Test
    void testAddItemTransaction() {
        Long itemId = 14L;
        AddItemTransaction addItem = new AddItemTransaction( itemId, "French Fries", 10.0 );
        addItem.withDescription( "Well it's French Fries what else to say!" )
                .withType( Type.FOOD )
                .withType( Type.STARTER )
                .withType( Type.HOT )
                .withType( Type.SNACK )
                .withCalories( 10000.0 )
                .withFat( 51.0 )
                .withProtein( 0.4 )
                .withCarbohydrates( 0.2 );
        addItem.execute();

        Item frenchFries = Database.getItemById( itemId );
        assertNotNull( frenchFries );
        assertEquals( "French Fries", frenchFries.getName() );
        assertEquals( 10.0, frenchFries.getPrice() );
        assertEquals( 10000.0, frenchFries.getCalories() );
        assertEquals( 51.0, frenchFries.getFat() );
        assertEquals( 0.4, frenchFries.getProtein() );
        assertEquals( 0.2, frenchFries.getCarbohydrates() );
        Set<Type> types = Set.of( Type.FOOD, Type.STARTER, Type.HOT, Type.SNACK );
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
}