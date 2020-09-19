package org.thekiddos.manager.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.api.mapper.ItemMapper;
import org.thekiddos.manager.api.mapper.TableMapper;
import org.thekiddos.manager.api.model.ItemDTO;
import org.thekiddos.manager.api.model.OrderedItemsDTO;
import org.thekiddos.manager.api.model.TableDTO;
import org.thekiddos.manager.api.model.TypeDTO;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.models.Type;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.services.ActiveTableService;
import org.thekiddos.manager.services.ItemService;
import org.thekiddos.manager.transactions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class ItemDTOControllerTest {
    private final Long itemId = 1L, itemId2 = 2L, tableId = 1L, tableId2 = 2L, customerId = 1L;
    @MockBean
    private ItemService itemService;
    private final ItemDTOController itemDTOController;
    private final ActiveTableService activeTableService;
    private final TableMapper tableMapper;
    private final ItemMapper itemMapper;
    private MockMvc mockMvc;

    @Autowired
    public ItemDTOControllerTest( ItemDTOController itemDTOController, ActiveTableService activeTableService, TableMapper tableMapper, ItemMapper itemMapper ) {
        this.itemDTOController = itemDTOController;
        this.activeTableService = activeTableService;
        this.tableMapper = tableMapper;
        this.itemMapper = itemMapper;
    }

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
        fillDatabase();
        mockMvc = MockMvcBuilders.standaloneSetup( itemDTOController ).build();
    }

    private void fillDatabase() {
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
        ItemDTO itemDTO = itemMapper.itemToItemDTO( Database.getItemById( itemId ) );
        // Since they are stored in a HashSet they appear in order
        TypeDTO typeDTO = new TypeDTO( "FOOD" ), typeDTO2 = new TypeDTO( "HOT" ), typeDTO3 = new TypeDTO( "STARTER" ), typeDTO4 = new TypeDTO( "SNACK" );
        ItemDTO itemDTO2 = itemMapper.itemToItemDTO( Database.getItemById( itemId2 ) );
        TypeDTO typeDTO5 = new TypeDTO( "COLD" ), typeDTO6 = new TypeDTO( "MILK" ), typeDTO7 = new TypeDTO( "SNACK" );
        Mockito.when( itemService.getItems() ).thenReturn( Arrays.asList(itemDTO, itemDTO2 ) );

        mockMvc.perform( get( "/api/items" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$[*]", hasSize( 1 ) ) )
                .andExpect( jsonPath( "$['items']", hasSize( 2 ) ) )
                .andExpect( jsonPath( "$['items'][0].id", is( itemDTO.getId().intValue() ) ) )
                .andExpect( jsonPath( "$['items'][0].name", is( itemDTO.getName() ) ) )
                .andExpect( jsonPath( "$['items'][0].price", is( itemDTO.getPrice() ) ) )
                .andExpect( jsonPath( "$['items'][0].calories", is( itemDTO.getCalories() ) ) )
                .andExpect( jsonPath( "$['items'][0].fat", is( itemDTO.getFat() ) ) )
                .andExpect( jsonPath( "$['items'][0].protein", is( itemDTO.getProtein() ) ) )
                .andExpect( jsonPath( "$['items'][0].carbohydrates", is( itemDTO.getCarbohydrates() ) ) )
                .andExpect( jsonPath( "$['items'][0].imagePath", is( itemDTO.getImagePath() ) ) )
                .andExpect( jsonPath( "$['items'][0].description", is( itemDTO.getDescription() ) ) )
                .andExpect( jsonPath( "$['items'][0]['types']", hasSize( itemDTO.getTypes().size() ) ) )
                .andExpect( jsonPath( "$['items'][0]['types'][0].name", is( typeDTO.getName() ) ) )
                .andExpect( jsonPath( "$['items'][0]['types'][1].name", is( typeDTO2.getName() ) ) )
                .andExpect( jsonPath( "$['items'][0]['types'][2].name", is( typeDTO3.getName() ) ) )
                .andExpect( jsonPath( "$['items'][0]['types'][3].name", is( typeDTO4.getName() ) ) )
                .andExpect( jsonPath( "$['items'][1].id", is( itemDTO2.getId().intValue() ) ) )
                .andExpect( jsonPath( "$['items'][1].name", is( itemDTO2.getName() ) ) )
                .andExpect( jsonPath( "$['items'][1].price", is( itemDTO2.getPrice() ) ) )
                .andExpect( jsonPath( "$['items'][1].calories", is( itemDTO2.getCalories() ) ) )
                .andExpect( jsonPath( "$['items'][1].fat", is( itemDTO2.getFat() ) ) )
                .andExpect( jsonPath( "$['items'][1].protein", is( itemDTO2.getProtein() ) ) )
                .andExpect( jsonPath( "$['items'][1].carbohydrates", is( itemDTO2.getCarbohydrates() ) ) )
                .andExpect( jsonPath( "$['items'][1].imagePath", is( itemDTO2.getImagePath() ) ) )
                .andExpect( jsonPath( "$['items'][1].description", is( itemDTO2.getDescription() ) ) )
                .andExpect( jsonPath( "$['items'][1]['types']", hasSize( itemDTO2.getTypes().size() ) ) )
                .andExpect( jsonPath( "$['items'][1]['types'][0].name", is( "COLD" ) ) )
                .andExpect( jsonPath( "$['items'][1]['types'][1].name", is( "MILK" ) ) )
                .andExpect( jsonPath( "$['items'][1]['types'][2].name", is( "SNACK" ) ) );
    }

    @Test
    void testAddOrderItems() throws Exception {
        List<Item> items = new ArrayList<>( Database.getItems() );
        Mockito.doAnswer( invocationOnMock -> {
            AddItemsToReservationTransaction serviceTransaction = new AddItemsToReservationTransaction( tableId );
            for ( Item item : items )
                serviceTransaction.addItem( item.getId() );
            serviceTransaction.execute();
            return null;
        }).when( itemService ).addItemsToOrder( ArgumentMatchers.anyLong(), ArgumentMatchers.anyList() );

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
        Mockito.doThrow( new IllegalArgumentException( "No Current Reservations for the selected table" ) )
                .when( itemService ).addItemsToOrder( ArgumentMatchers.anyLong(), ArgumentMatchers.anyList() );

        TableDTO inActiveTable = tableMapper.tableToTableDTO( Database.getTableById( tableId2 ) );
        OrderedItemsDTO orderedItemsDTO = new OrderedItemsDTO( inActiveTable, itemService.getItems() );
        String orderedItemsJson = toJson( orderedItemsDTO );
        mockMvc.perform( post( "/api/items" ).content( orderedItemsJson ).contentType( "application/json;charset=UTF-8" ) )
                .andExpect( status().isBadRequest() )
                .andExpect( content().string( "No Current Reservations for the selected table" ) );
    }

    @Test
    void testAddOrderItemsWithItemsThatDoesNotExists() throws Exception {
        Mockito.doThrow( new IllegalArgumentException( "One or more of the provided items are not served by the restaurant." ) )
                .when( itemService ).addItemsToOrder( ArgumentMatchers.anyLong(), ArgumentMatchers.anyList() );

        // This is not needed since we are mocking. but keeping it in case I decided to do integration test.
        ItemDTO invalidItemDTO = new ItemDTO();
        invalidItemDTO.setId( Util.INVALID_ID );
        invalidItemDTO.setName( "Haha" );

        List<ItemDTO> itemsWithInvalidItem = itemService.getItems();
        itemsWithInvalidItem.add( invalidItemDTO );

        OrderedItemsDTO orderedItemsDTO = new OrderedItemsDTO( activeTableService.getActiveTableById( tableId ), itemsWithInvalidItem );
        String orderedItemsJson = toJson( orderedItemsDTO );
        mockMvc.perform( post( "/api/items" ).content( orderedItemsJson ).contentType( "application/json;charset=UTF-8" ) )
                .andExpect( status().isBadRequest() )
                .andExpect( content().string( "One or more of the provided items are not served by the restaurant." ) );
    }

    private String toJson( Object object ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString( object );
    }

    // TODO test failing cases
}