package org.thekiddos.manager.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thekiddos.manager.api.model.TypeListDTO;
import org.thekiddos.manager.services.TypeService;

@Controller
@RequestMapping("/api/types")
public class TypeDTOController {
    private TypeService typeService;

    public TypeDTOController( TypeService typeService ) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<TypeListDTO> getTypes() {
        return new ResponseEntity<>( new TypeListDTO( typeService.getTypes() ), HttpStatus.OK );
    }
}
