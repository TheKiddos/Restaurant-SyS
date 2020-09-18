package org.thekiddos.manager.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thekiddos.manager.api.model.TypeDTO;
import org.thekiddos.manager.services.TypeService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TypeDTOControllerTest {
    @Mock
    private TypeService typeService;
    @InjectMocks
    private TypeDTOController typeDTOController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks( this );
        mockMvc = MockMvcBuilders.standaloneSetup( typeDTOController ).build();
    }

    @Test
    void testGetTypes() throws Exception {
        TypeDTO typeDTO1 = new TypeDTO( "FOOD" );
        TypeDTO typeDTO2 = new TypeDTO( "HOT" );
        TypeDTO typeDTO3 = new TypeDTO( "SNACK" );

        List<TypeDTO> types = Arrays.asList( typeDTO1, typeDTO2, typeDTO3 );
        when( typeService.getTypes() ).thenReturn( types );

        mockMvc.perform( get( "/api/types" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$[*]", hasSize( 1 ) ) )
                .andExpect( jsonPath( "$['types']", hasSize( 3 ) ) )
                .andExpect( jsonPath( "$['types'][0].name", is( typeDTO1.getName() ) ) )
                .andExpect( jsonPath( "$['types'][1].name", is( typeDTO2.getName() ) ) )
                .andExpect( jsonPath( "$['types'][2].name", is( typeDTO3.getName() ) ) );
    }
}