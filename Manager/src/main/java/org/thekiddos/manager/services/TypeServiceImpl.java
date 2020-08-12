package org.thekiddos.manager.services;

import org.springframework.stereotype.Service;
import org.thekiddos.manager.api.mapper.TypeMapper;
import org.thekiddos.manager.api.model.TypeDTO;
import org.thekiddos.manager.repositories.Database;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl implements TypeService {
    private final TypeMapper typeMapper;

    public TypeServiceImpl( TypeMapper typeMapper ) {
        this.typeMapper = typeMapper;
    }

    @Override
    public List<TypeDTO> getTypes() {
        return Database.getTypes().stream().map( typeMapper::typeToTypeDTO ).collect( Collectors.toList());
    }
}
