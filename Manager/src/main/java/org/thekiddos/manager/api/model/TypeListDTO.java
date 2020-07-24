package org.thekiddos.manager.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TypeListDTO implements Serializable {
    private List<TypeDTO> types;
}
