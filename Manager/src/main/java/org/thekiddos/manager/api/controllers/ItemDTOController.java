package org.thekiddos.manager.api.controllers;

import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.api.mapper.ItemMapper;
import org.thekiddos.manager.api.model.ItemListDTO;
import org.thekiddos.manager.api.model.OrderedItemsDTO;
import org.thekiddos.manager.gui.controllers.OrderController;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.services.ActiveTableService;
import org.thekiddos.manager.services.ItemService;
import org.thekiddos.manager.transactions.AddItemsToReservationTransaction;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/api/items")
public class ItemDTOController {
    private final ItemService itemService;
    private final OrderController orderController;
    private final ItemMapper itemMapper;
    private final ActiveTableService activeTableService;

    @Autowired
    public ItemDTOController( ItemService itemService, OrderController orderController, ItemMapper itemMapper, ActiveTableService activeTableService ) {
        this.itemService = itemService;
        this.orderController = orderController;
        this.itemMapper = itemMapper;
        this.activeTableService = activeTableService;
    }

    @GetMapping
    public ResponseEntity<ItemListDTO> getItems() {
        return new ResponseEntity<>( new ItemListDTO( itemService.getItems() ), HttpStatus.OK );
    }

    @PostMapping
    public ResponseEntity<Object> addOrderItems( @RequestBody OrderedItemsDTO orderedItemsDTO ) {
        List<Item> orderedItems = orderedItemsDTO.getItems().stream().map( itemMapper::itemDTOToItem ).collect( Collectors.toList() );

        if ( !itemService.allItemsInMenu( orderedItems ) )
            return new ResponseEntity<>( "Some/All items doesn't exists in this restaurant services", HttpStatus.BAD_REQUEST );

        Long tableId = orderedItemsDTO.getTable().getId();
        if ( activeTableService.getActiveTableById( tableId ) == null )
            return new ResponseEntity<>( "The table you selected isn't active", HttpStatus.BAD_REQUEST );

        if ( Util.isGuiInitialized() ) {
            Platform.runLater( () -> orderController.showAddItemsToOrderDialog( tableId, orderedItems ) );
        }
        else {
            log.warn( "GUI is not initialized. Testing mode is assumed. This means added items will be accepted without confirmation" );
            addItemsToOrder( orderedItems, tableId );
        }

        return new ResponseEntity<>( "Order sent successfully", HttpStatus.CREATED );
    }

    // This should not be here move later
    private void addItemsToOrder( List<Item> orderedItems, Long tableId ) {
        AddItemsToReservationTransaction serviceTransaction = new AddItemsToReservationTransaction( tableId );
        for ( Item item : orderedItems )
            serviceTransaction.addItem( item.getId() );
        serviceTransaction.execute();
    }
}
