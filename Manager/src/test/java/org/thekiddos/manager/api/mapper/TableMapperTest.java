package org.thekiddos.manager.api.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.api.model.TableDTO;
import org.thekiddos.manager.models.Table;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class TableMapperTest {
    private TableMapper tableMapper;

    private final Long id = 1L;
    private final int maxCapacity = 4;
    private final double fee = 10.0;

    @Autowired
    public TableMapperTest( TableMapper tableMapper ) {
        this.tableMapper = tableMapper;
    }

    @Test
    void testTableToTableDTO() {
        Table table = new Table( id, maxCapacity, fee );

        TableDTO tableDTO = tableMapper.tableToTableDTO( table );

        assertEquals( id, tableDTO.getId() );
        assertEquals( maxCapacity, tableDTO.getMaxCapacity() );
        assertEquals( fee, tableDTO.getFee() );
    }
}