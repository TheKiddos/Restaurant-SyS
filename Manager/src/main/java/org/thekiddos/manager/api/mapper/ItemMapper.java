package org.thekiddos.manager.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.api.model.ItemDTO;
import org.thekiddos.manager.models.Item;

@Mapper(componentModel = "spring")
@Component
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper( ItemMapper.class );

    ItemDTO itemToItemDTO( Item item );
}