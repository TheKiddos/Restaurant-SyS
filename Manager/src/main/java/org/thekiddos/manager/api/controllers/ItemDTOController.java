package org.thekiddos.manager.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thekiddos.manager.api.model.ItemListDTO;
import org.thekiddos.manager.services.ItemService;

@Controller
@RequestMapping("/api/items")
public class ItemDTOController {
    private ItemService itemService;

    public ItemDTOController( ItemService itemService ) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<ItemListDTO> getItems() {
        return new ResponseEntity<>( new ItemListDTO( itemService.getItems() ), HttpStatus.OK );
    }
}
