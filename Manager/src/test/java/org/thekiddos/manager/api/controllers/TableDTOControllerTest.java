package org.thekiddos.manager.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddCustomerTransaction;
import org.thekiddos.manager.transactions.AddTableTransaction;
import org.thekiddos.manager.transactions.ImmediateReservationTransaction;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class TableDTOControllerTest {
    private final Long tableId = 1L, tableId2 = 2L, customerId = 1L, customerId2 = 2L;
    private final TableDTOController tableDTOController;
    private MockMvc mockMvc;

    @Autowired
    TableDTOControllerTest( TableDTOController tableDTOController ) {
        this.tableDTOController = tableDTOController;
    }

    @BeforeEach
    void setUpDatabase() {
        // The test uses the actual database and it might be sensitive to the order of retrieving and storing the objects
        Database.deleteAll();
        fillDatabase();
        mockMvc = MockMvcBuilders.standaloneSetup( tableDTOController ).build();
    }

    void fillDatabase() {
        new AddTableTransaction( tableId ).execute();
        new AddTableTransaction( tableId2 ).execute();
        new AddCustomerTransaction( customerId, "Kiddo", "mp4-12cs5@outlook.com", "12345678" ).execute();
        new AddCustomerTransaction( customerId2, "Kiddo2", "mp412cs5@outlook.com", "12345678" ).execute();

        new ImmediateReservationTransaction( tableId, customerId ).execute();
        new ImmediateReservationTransaction( tableId2, customerId2 ).execute();
    }

    @Test
    void testGetActiveTables() throws Exception {
        mockMvc.perform( get( "/api/tables" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$[*]", hasSize( 1 ) ) )
                .andExpect( jsonPath( "$['tables']", hasSize( 2 ) ) )
                .andExpect( jsonPath( "$['tables'][0].id", is( 2 ) ) )
                .andExpect( jsonPath( "$['tables'][0].maxCapacity", is( 4 ) ) )
                .andExpect( jsonPath( "$['tables'][0].fee", is( 0.0 ) ) )
                .andExpect( jsonPath( "$['tables'][1].id", is( 1 ) ) )
                .andExpect( jsonPath( "$['tables'][1].maxCapacity", is( 4 ) ) )
                .andExpect( jsonPath( "$['tables'][1].fee", is( 0.0 ) ) );
    }

    @Test
    void testGetActiveTable() throws Exception {
        mockMvc.perform( get( "/api/tables/" + tableId ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$[*]", hasSize( 3 ) ) )
                .andExpect( jsonPath( "$['id']", is( 1 ) ) )
                .andExpect( jsonPath( "$['maxCapacity']", is( 4 ) ) )
                .andExpect( jsonPath( "$['fee']", is( 0.0 ) ) );
    }
}