package org.thekiddos.manager.models;

import lombok.*;

import javax.persistence.Table;
import javax.persistence.*;
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
@Table(name = "items")
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
    @Getter @Column(name = "image")
    private String imagePath;
    @Getter
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Type> types = new HashSet<>();

    /**
     * @return a set of this items {@link Type}s
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

    /**
     * While the Object equals method only compares the Id of the items
     * this method compares all important fields (name, price, ...etc). This is especially useful when comparing the item to another item which was
     * generated outside the current program eg: REST API Post request.
     * @param other The item to compare the current item to
     * @return true if both items match, false otherwise.
     */
    public boolean deepEquals( Item other ) {
        if ( other == null )
            return false;
        return id.equals( other.getId() ) &&
                name.equals( other.getName() ) &&
                price == other.getPrice() &&
                types.equals( other.getTypes() );
    }
}
