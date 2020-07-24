package org.thekiddos.manager.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.thekiddos.manager.api.model.TableDTO;
import org.thekiddos.manager.models.Table;

@Mapper(componentModel = "spring")
public interface TableMapper {
    TableMapper INSTANCE = Mappers.getMapper( TableMapper.class );

    TableDTO tableToTableDTO( Table table );
}
