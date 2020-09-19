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
        Long tableId = orderedItemsDTO.getTable().getId();

        try {
            if ( Util.isGuiInitialized() ) {
                Platform.runLater( () -> orderController.showAddItemsToOrderDialog( tableId, orderedItems ) );
            }
            else {
                log.warn( "GUI is not initialized. Testing mode is assumed. This means added items will be accepted without confirmation" );
                itemService.addItemsToOrder( tableId, orderedItems );
            }
        }
        catch ( IllegalArgumentException exception ) {
            return new ResponseEntity<>( exception.getMessage(), HttpStatus.BAD_REQUEST );
        }

        return new ResponseEntity<>( "Order sent successfully", HttpStatus.CREATED );
    }
}
