package org.thekiddos.manager.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.api.mapper.TableMapper;
import org.thekiddos.manager.api.model.ItemDTO;
import org.thekiddos.manager.api.model.OrderedItemsDTO;
import org.thekiddos.manager.api.model.TableDTO;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.models.Type;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.services.ActiveTableService;
import org.thekiddos.manager.services.ItemService;
import org.thekiddos.manager.transactions.AddCustomerTransaction;
import org.thekiddos.manager.transactions.AddItemTransaction;
import org.thekiddos.manager.transactions.AddTableTransaction;
import org.thekiddos.manager.transactions.ImmediateReservationTransaction;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class ItemDTOControllerTest {
    private final Long itemId = 1L, itemId2 = 2L, tableId = 1L, tableId2 = 2L, customerId = 1L;
    private final ItemDTOController itemDTOController;
    private final ItemService itemService;
    private final ActiveTableService activeTableService;
    private final TableMapper tableMapper;
    private MockMvc mockMvc;

    @Autowired
    public ItemDTOControllerTest( ItemDTOController itemDTOController, ItemService itemService, ActiveTableService activeTableService, TableMapper tableMapper ) {
        this.itemDTOController = itemDTOController;
        this.itemService = itemService;
        this.activeTableService = activeTableService;
        this.tableMapper = tableMapper;
    }

    @BeforeEach
    void setUpDatabase() {
        // The test uses the actual database and it might be sensitive to the order of retrieving and storing the objects
        Database.deleteAll();
        fillDatabase();
        mockMvc = MockMvcBuilders.standaloneSetup( itemDTOController ).build();
    }

    private void fillDatabase() {
        // Uses the actual database so sensitive to retrieving order and stuff.
        String imagePath = "https://cms.splendidtable.org/sites/default/files/styles/w2000/public/french-fries.jpg?itok=FS-YwUYH";
        AddItemTransaction addItem = new AddItemTransaction( itemId, "French Fries", 10.0 );
        addItem.withDescription( "Well it's French Fries what else to say!" )
                .withType( Type.type( "FOOD" ) )
                .withType( Type.type( "STARTER" ) )
                .withType( Type.type( "HOT" ) )
                .withType( Type.type( "SNACK" ) )
                .withCalories( 10000.0 )
                .withFat( 51.0 )
                .withProtein( 0.4 )
                .withCarbohydrates( 0.2 )
                .withImage( imagePath );
        addItem.execute();

        imagePath = "Yah Yah Yah";
        addItem = new AddItemTransaction( itemId2, "Batata", 100.0 );
        addItem.withDescription( "Batataaaaaa" )
                .withType( Type.type( "COLD" ) )
                .withType( Type.type( "MILK" ) )
                .withType( Type.type( "SNACK" ) )
                .withCalories( 100.0 )
                .withFat( 5.0 )
                .withProtein( 1.0 )
                .withCarbohydrates( 1.0 )
                .withImage( imagePath );
        addItem.execute();

        new AddTableTransaction( tableId ).execute();
        new AddTableTransaction( tableId2 ).execute();
        new AddCustomerTransaction( customerId, "Kiddo", "mp4-12cs5@outlook.com", "12345678" ).execute();
        new ImmediateReservationTransaction( tableId, customerId ).execute();
    }

    @Test
    void testGetItems() throws Exception {
        mockMvc.perform( get( "/api/items" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$[*]", hasSize( 1 ) ) )
                .andExpect( jsonPath( "$['items']", hasSize( 2 ) ) )
                .andExpect( jsonPath( "$['items'][0].id", is( 1 ) ) )
                .andExpect( jsonPath( "$['items'][0].name", is( "French Fries" ) ) )
                .andExpect( jsonPath( "$['items'][0].price", is( 10.0 ) ) )
                .andExpect( jsonPath( "$['items'][0].calories", is( 10000.0 ) ) )
                .andExpect( jsonPath( "$['items'][0].fat", is( 51.0 ) ) )
                .andExpect( jsonPath( "$['items'][0].protein", is( 0.4 ) ) )
                .andExpect( jsonPath( "$['items'][0].carbohydrates", is( 0.2 ) ) )
                .andExpect( jsonPath( "$['items'][0].imagePath", is( "https://cms.splendidtable.org/sites/default/files/styles/w2000/public/french-fries.jpg?itok=FS-YwUYH" ) ) )
                .andExpect( jsonPath( "$['items'][0].description", is( "Well it's French Fries what else to say!" ) ) )
                .andExpect( jsonPath( "$['items'][0]['types']", hasSize( 4 ) ) )
                .andExpect( jsonPath( "$['items'][0]['types'][0].name", is( "FOOD" ) ) )
                .andExpect( jsonPath( "$['items'][0]['types'][1].name", is( "STARTER" ) ) )
                .andExpect( jsonPath( "$['items'][0]['types'][2].name", is( "HOT" ) ) )
                .andExpect( jsonPath( "$['items'][0]['types'][3].name", is( "SNACK" ) ) )
                .andExpect( jsonPath( "$['items'][1].id", is( 2 ) ) )
                .andExpect( jsonPath( "$['items'][1].name", is( "Batata" ) ) )
                .andExpect( jsonPath( "$['items'][1].price", is( 100.0 ) ) )
                .andExpect( jsonPath( "$['items'][1].calories", is( 100.0 ) ) )
                .andExpect( jsonPath( "$['items'][1].fat", is( 5.0 ) ) )
                .andExpect( jsonPath( "$['items'][1].protein", is( 1.0 ) ) )
                .andExpect( jsonPath( "$['items'][1].carbohydrates", is( 1.0 ) ) )
                .andExpect( jsonPath( "$['items'][1].imagePath", is( "Yah Yah Yah" ) ) )
                .andExpect( jsonPath( "$['items'][1].description", is( "Batataaaaaa" ) ) )
                .andExpect( jsonPath( "$['items'][1]['types']", hasSize( 3 ) ) )
                .andExpect( jsonPath( "$['items'][1]['types'][0].name", is( "COLD" ) ) )
                .andExpect( jsonPath( "$['items'][1]['types'][1].name", is( "MILK" ) ) )
                .andExpect( jsonPath( "$['items'][1]['types'][2].name", is( "SNACK" ) ) );
    }

    @Test
    void testAddOrderItems() throws Exception {
        OrderedItemsDTO orderedItemsDTO = new OrderedItemsDTO( activeTableService.getActiveTableById( tableId ), itemService.getItems() );
        String orderedItemsJson = toJson( orderedItemsDTO );
        mockMvc.perform( post( "/api/items" ).content( orderedItemsJson ).contentType( "application/json;charset=UTF-8" ) )
                .andExpect( status().isCreated() );

        Reservation reservation = Database.getCurrentReservationByTableId( tableId );
        assertEquals( 2, reservation.getOrder().getItemsQuantities().size() );
        assertEquals( 1, reservation.getOrder().getItemsQuantities().get( Database.getItemById( itemId ) ) );
        assertEquals( 1, reservation.getOrder().getItemsQuantities().get( Database.getItemById( itemId2 ) ) );
    }

    @Test
    void testAddOrderItemsWithInActiveTable() throws Exception {
        TableDTO inActiveTable = tableMapper.tableToTableDTO( Database.getTableById( tableId2 ) );
        OrderedItemsDTO orderedItemsDTO = new OrderedItemsDTO( inActiveTable, itemService.getItems() );
        String orderedItemsJson = toJson( orderedItemsDTO );
        mockMvc.perform( post( "/api/items" ).content( orderedItemsJson ).contentType( "application/json;charset=UTF-8" ) )
                .andExpect( status().isBadRequest() );
    }

    @Test
    void testAddOrderItemsWithItemsThatDoesNotExists() throws Exception {
        ItemDTO invalidItemDTO = new ItemDTO();
        invalidItemDTO.setId( Util.INVALID_ID );

        List<ItemDTO> itemsWithInvalidItem = itemService.getItems();
        itemsWithInvalidItem.add( invalidItemDTO );

        OrderedItemsDTO orderedItemsDTO = new OrderedItemsDTO( activeTableService.getActiveTableById( tableId ), itemsWithInvalidItem );
        String orderedItemsJson = toJson( orderedItemsDTO );
        mockMvc.perform( post( "/api/items" ).content( orderedItemsJson ).contentType( "application/json;charset=UTF-8" ) )
                .andExpect( status().isBadRequest() );
    }

    private String toJson( Object object ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString( object );
    }

    // TODO test failing cases
}