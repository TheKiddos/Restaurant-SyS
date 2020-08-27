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
import org.thekiddos.manager.models.Type;
import org.thekiddos.manager.repositories.Database;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class TypeDTOControllerTest {
    private final TypeDTOController typeDTOController;
    private MockMvc mockMvc;

    @Autowired
    public TypeDTOControllerTest( TypeDTOController typeDTOController ) {
        this.typeDTOController = typeDTOController;
    }

    @BeforeEach
    void setUpDatabase() {
        Database.deleteTypes();
        fillDatabase();
        mockMvc = MockMvcBuilders.standaloneSetup( typeDTOController ).build();
    }

    void fillDatabase() {
        // Note the MVC tests use the actual database, this might create a problem with the order of adding/retrieving
        Type.type( "FOOD" );
        Type.type( "HOT" );
        Type.type( "SNACK" );
    }

    @Test
    void testGetTypes() throws Exception {
        mockMvc.perform( get( "/api/types" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$[*]", hasSize( 1 ) ) )
                .andExpect( jsonPath( "$['types']", hasSize( 3 ) ) )
                .andExpect( jsonPath( "$['types'][0].name", is( "FOOD" ) ) )
                .andExpect( jsonPath( "$['types'][1].name", is( "HOT" ) ) )
                .andExpect( jsonPath( "$['types'][2].name", is( "SNACK" ) ) );
    }
}