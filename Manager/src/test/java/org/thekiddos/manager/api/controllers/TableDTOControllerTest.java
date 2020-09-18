package org.thekiddos.manager.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thekiddos.manager.api.model.TableDTO;
import org.thekiddos.manager.services.ActiveTableService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TableDTOControllerTest {
    private static final Long TABLE_ID_1 = 1L;
    private static final Long TABLE_ID_2 = 2L;
    private static final int CAPACITY = 4;
    private static final double FEE = 0.0;
    @Mock
    private ActiveTableService activeTableService;
    @InjectMocks
    private TableDTOController tableDTOController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks( this );

        TableDTO tableDTO1 = new TableDTO( TABLE_ID_1, CAPACITY, FEE );
        TableDTO tableDTO2 = new TableDTO( TABLE_ID_2, CAPACITY, FEE );

        Mockito.when( activeTableService.getActiveTables() ).thenReturn( Arrays.asList( tableDTO1, tableDTO2 ) );
        Mockito.when( activeTableService.getActiveTableById( TABLE_ID_1 ) ).thenReturn( tableDTO1 );
        mockMvc = MockMvcBuilders.standaloneSetup( tableDTOController ).build();
    }

    @Test
    void testGetActiveTables() throws Exception {
        mockMvc.perform( get( "/api/tables" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$[*]", hasSize( 1 ) ) )
                .andExpect( jsonPath( "$['tables']", hasSize( 2 ) ) )
                .andExpect( jsonPath( "$['tables'][0].id", is( TABLE_ID_1.intValue() ) ) )
                .andExpect( jsonPath( "$['tables'][0].maxCapacity", is( CAPACITY ) ) )
                .andExpect( jsonPath( "$['tables'][0].fee", is( FEE ) ) )
                .andExpect( jsonPath( "$['tables'][1].id", is( TABLE_ID_2.intValue() ) ) )
                .andExpect( jsonPath( "$['tables'][1].maxCapacity", is( CAPACITY ) ) )
                .andExpect( jsonPath( "$['tables'][1].fee", is( FEE ) ) );
    }

    @Test
    void testGetActiveTable() throws Exception {
        mockMvc.perform( get( "/api/tables/" + TABLE_ID_1 ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$[*]", hasSize( 3 ) ) )
                .andExpect( jsonPath( "$['id']", is( TABLE_ID_1.intValue() ) ) )
                .andExpect( jsonPath( "$['maxCapacity']", is( CAPACITY ) ) )
                .andExpect( jsonPath( "$['fee']", is( FEE ) ) );
    }
}