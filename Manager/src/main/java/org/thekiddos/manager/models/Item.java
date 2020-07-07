package org.thekiddos.manager.models;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Item {
    @NonNull @Id
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

    @ElementCollection(targetClass = Type.class, fetch = FetchType.EAGER)
    private Set<Type> types = new HashSet<>();

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
