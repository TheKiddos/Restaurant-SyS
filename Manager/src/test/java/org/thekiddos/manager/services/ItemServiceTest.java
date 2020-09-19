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
import org.thekiddos.manager.models.Type;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddItemTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class ItemServiceTest {
    private final Long itemId = 1L, itemId2 = 2L;
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
        AddItemTransaction addItem = new AddItemTransaction( itemId, "French Fries", 10.0 );
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
        addItem = new AddItemTransaction( itemId2, "Batata", 100.0 );
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
        assertFalse( itemService.allItemsInMenu( List.of( item, Database.getItemById( itemId ), Database.getItemById( itemId2 ) ) ) );
    }
}