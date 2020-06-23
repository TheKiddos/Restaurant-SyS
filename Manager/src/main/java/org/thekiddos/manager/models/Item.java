package org.thekiddos.manager.models;

import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Objects;
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

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Item item = (Item) o;
        return id.equals( item.id );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }
}
