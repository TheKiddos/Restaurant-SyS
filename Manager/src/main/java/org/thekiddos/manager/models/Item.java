package org.thekiddos.manager.models;

import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Data
public class Item {
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
    private Set<Type> types = new HashSet<>();
    private String imagePath;
    private String description;

    public void addType( Type type ) {
        types.add( type );
    }
}
