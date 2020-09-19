package org.thekiddos.manager.api.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.api.model.TypeDTO;
import org.thekiddos.manager.models.Type;

@Mapper(componentModel = "spring")
@Component
public interface TypeMapper {
    TypeDTO typeToTypeDTO( Type type );

    default Type typeDTOToType( TypeDTO typeDTO ) {
        return typeDTO == null ? null : Type.type( typeDTO.getName() );
    }
}
