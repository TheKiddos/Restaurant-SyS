package org.thekiddos.manager.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.api.model.TypeDTO;
import org.thekiddos.manager.models.Type;

@Mapper(componentModel = "spring")
@Component
public interface TypeMapper {
    TypeMapper INSTANCE = Mappers.getMapper( TypeMapper.class );

    TypeDTO typeToTypeDTO( Type type );
}
