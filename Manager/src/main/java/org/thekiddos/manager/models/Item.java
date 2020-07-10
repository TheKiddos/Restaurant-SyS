package org.thekiddos.manager.models;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * An item represents food and beverages that a {@link Customer} can {@link Order}
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Item {
    @NonNull @Id @Getter
    private Long id;
    @NonNull @Getter
    private String name;
    @NonNull @Getter
    private double price;
    @Getter
    private double calories;
    @Getter
    private double fat;
    @Getter
    private double protein;
    @Getter
    private double carbohydrates;
    @Getter
    private String imagePath;
    @Getter
    private String description;

    @ElementCollection(targetClass = Type.class, fetch = FetchType.EAGER)
    private Set<Type> types = new HashSet<>();

    /**
     * Returns a set of this items {@link Type}s
     * @return
     */
    public Set<Type> getTypes() {
        return Collections.unmodifiableSet( types );
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
