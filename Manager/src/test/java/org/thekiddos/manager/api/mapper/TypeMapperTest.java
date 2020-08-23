package org.thekiddos.manager.api.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.api.model.TypeDTO;
import org.thekiddos.manager.models.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class TypeMapperTest {
    private TypeMapper typeMapper;

    private final String typeName = "FOOD";

    @Autowired
    public TypeMapperTest( TypeMapper typeMapper ) {
        this.typeMapper = typeMapper;
    }

    @Test
    void testTypeToTypeDTO() {
        Type type = Type.type( typeName );

        TypeDTO typeDTO = typeMapper.typeToTypeDTO( type );

        assertEquals( typeName, typeDTO.getName() );
    }
}