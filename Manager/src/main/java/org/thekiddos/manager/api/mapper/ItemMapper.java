package org.thekiddos.manager.api.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.api.model.ItemDTO;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.repositories.Database;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
public interface ItemMapper {
    ItemDTO itemToItemDTO( Item item );

    default Item itemDTOToItem(ItemDTO itemDTO) {
        if ( itemDTO == null ) {
            return null;
        }

        TypeMapper typeMapper = Database.getBean( TypeMapper.class );

        return new Item(
                itemDTO.getId(),
                itemDTO.getName(),
                itemDTO.getPrice(),
                itemDTO.getCalories(),
                itemDTO.getFat(),
                itemDTO.getProtein(),
                itemDTO.getCarbohydrates(),
                itemDTO.getImagePath(),
                itemDTO.getDescription(),
                itemDTO.getTypes().stream().map( typeMapper::typeDTOToType ).collect( Collectors.toSet() )
        );
    }
}