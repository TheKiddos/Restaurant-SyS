package org.thekiddos.manager.api.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.api.model.ItemDTO;
import org.thekiddos.manager.api.model.TypeDTO;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.models.Type;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class ItemMapperTest {
    private final ItemMapper itemMapper;
    private final TypeMapper typeMapper;

    private final Long id = 1L;
    private final String name = "French Fries";
    private final double price = 10.0;
    private final double calories = 1000.0;
    private final double fat = 10.0;
    private final double protein = 1.0;
    private final double carbohydrates = 5.0;
    private final String imagePath = "Hello";
    private final String description = "Batataaaaaaaaaa";

    private final Set<Type> types = Set.of( Type.type( "HOT" ), Type.type( "STARTER" ), Type.type( "FOOD" ) );
    private final Set<TypeDTO> typeDTOs;

    @Autowired
    public ItemMapperTest( ItemMapper itemMapper, TypeMapper typeMapper ) {
        this.itemMapper = itemMapper;
        this.typeMapper = typeMapper;
        typeDTOs = types.stream().map( typeMapper::typeToTypeDTO ).collect( Collectors.toSet() );
    }

    @Test
    void testItemToItemDTO() {
        Item item = new Item( id, name, price, calories, fat, protein, carbohydrates, imagePath,
                description, new HashSet<>( types ) );

        ItemDTO itemDTO = itemMapper.itemToItemDTO( item );

        assertEquals( id, itemDTO.getId() );
        assertEquals( name, itemDTO.getName() );
        assertEquals( price, itemDTO.getPrice() );
        assertEquals( calories, itemDTO.getCalories() );
        assertEquals( fat, itemDTO.getFat() );
        assertEquals( protein, itemDTO.getProtein() );
        assertEquals( carbohydrates, itemDTO.getCarbohydrates() );
        assertEquals( description, itemDTO.getDescription() );
        assertEquals( imagePath, itemDTO.getImagePath() );
        assertEquals( typeDTOs, itemDTO.getTypes() );
    }

    @Test
    void testItemDTOToItem() {
        ItemDTO itemDTO = new ItemDTO( id, name, price, calories, fat, protein, carbohydrates, imagePath,
                description, typeDTOs );

        Item item = itemMapper.itemDTOToItem( itemDTO );

        assertEquals( id, item.getId() );
        assertEquals( name, item.getName() );
        assertEquals( price, item.getPrice() );
        assertEquals( calories, item.getCalories() );
        assertEquals( fat, item.getFat() );
        assertEquals( protein, item.getProtein() );
        assertEquals( carbohydrates, item.getCarbohydrates() );
        assertEquals( description, item.getDescription() );
        assertEquals( imagePath, item.getImagePath() );
        assertEquals( types, item.getTypes() );
    }
}