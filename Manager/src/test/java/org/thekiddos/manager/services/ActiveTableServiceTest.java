package org.thekiddos.manager.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.api.model.TableDTO;
import org.thekiddos.manager.models.Table;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddCustomerTransaction;
import org.thekiddos.manager.transactions.AddTableTransaction;
import org.thekiddos.manager.transactions.ImmediateReservationTransaction;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class ActiveTableServiceTest {
    private final Long tableId = 1L, tableId2 = 2L, customerId = 1L, customerId2 = 2L;
    private ActiveTableService activeTableService;

    @Autowired
    public ActiveTableServiceTest( ActiveTableService activeTableService ) {
        this.activeTableService = activeTableService;
    }

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
        fillDatabase();
    }

    private void fillDatabase() {
        new AddTableTransaction( tableId ).execute();
        new AddTableTransaction( tableId2 ).execute();
        new AddCustomerTransaction( customerId, "Kiddo", "mp4-12cs5@outlook.com", "12345678" ).execute();
        new AddCustomerTransaction( customerId2, "Kiddo2", "mp412cs5@outlook.com", "12345678" ).execute();

        new ImmediateReservationTransaction( tableId, customerId ).execute();
        new ImmediateReservationTransaction( tableId2, customerId2 ).execute();
    }

    @Test
    void testGetActiveTables() {
        List<TableDTO> activeTables = activeTableService.getActiveTables();

        assertEquals( 2, activeTables.size() );

        for ( TableDTO tableDTO : activeTables ) {
            validateActiveTableDTO( tableDTO );
        }
    }

    @Test
    void testGetActiveTableById() {
        TableDTO tableDTO = activeTableService.getActiveTableById( tableId );
        validateActiveTableDTO( tableDTO );

        tableDTO = activeTableService.getActiveTableById( tableId2 );
        validateActiveTableDTO( tableDTO );
    }

    private void validateActiveTableDTO( TableDTO tableDTO ) {
        Table table = Database.getTableById( tableDTO.getId() );
        assertNotNull( table );
        assertTrue( table.isReserved( LocalDate.now() ) );
        assertEquals( table.getFee(), tableDTO.getFee() );
        assertEquals( table.getMaxCapacity(), tableDTO.getMaxCapacity() );
    }
}