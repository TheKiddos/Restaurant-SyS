package org.thekiddos.manager.api.model;

import lombok.*;
import org.thekiddos.manager.models.Type;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemDTO implements Serializable {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private double price;
    private double calories;
    private double fat;
    private double protein;
    private double carbohydrates;
    private String imagePath;
    private String description;

    private Set<Type> types = new HashSet<>();
}
