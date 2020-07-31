package org.thekiddos.manager.api.controllers;

import javafx.application.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thekiddos.manager.api.mapper.ItemMapper;
import org.thekiddos.manager.api.mapper.TableMapper;
import org.thekiddos.manager.api.model.ItemListDTO;
import org.thekiddos.manager.api.model.OrderedItemsDTO;
import org.thekiddos.manager.gui.controllers.OrderController;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.services.ItemService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/items")
public class ItemDTOController {
    private ItemService itemService;
    private OrderController orderController;
    private ItemMapper itemMapper;
    private TableMapper tableMapper;

    @Autowired
    public ItemDTOController( ItemService itemService, OrderController orderController, ItemMapper itemMapper, TableMapper tableMapper ) {
        this.itemService = itemService;
        this.orderController = orderController;
        this.itemMapper = itemMapper;
        this.tableMapper = tableMapper; // remove
    }

    @GetMapping
    public ResponseEntity<ItemListDTO> getItems() {
        return new ResponseEntity<>( new ItemListDTO( itemService.getItems() ), HttpStatus.OK );
    }

    @PostMapping
    public ResponseEntity<Object> addOrderItems( @RequestBody OrderedItemsDTO orderedItemsDTO ) {
        List<Item> orderedItems = orderedItemsDTO.getItems().stream().map( itemDTO -> itemMapper.itemDTOToItem( itemDTO ) )
                .collect( Collectors.toList() );
        Platform.runLater( () -> orderController.showAddItemsToOrderDialog( orderedItemsDTO.getTable().getId(), orderedItems ) );
        return new ResponseEntity<>( "Order sent successfully", HttpStatus.CREATED );
    }
}
