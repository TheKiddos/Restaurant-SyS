package org.thekiddos.manager.transactions;

import lombok.Getter;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.models.Type;
import org.thekiddos.manager.repositories.Database;

import java.util.HashSet;
import java.util.Set;

/**
 * This is used to add an {@link Item}
 * this transaction is designed to mimic the builder pattern
 * so all optional Item properties can be added with this classes methods and they can be
 * cascaded and mixed in any order.
 * The Item id must be unique or an {@link IllegalArgumentException} will be thrown
 */
@Getter
public class AddItemTransaction implements Transaction {
    private Long id;
    private String name;
    private double price;
    private double calories;
    private double fat;
    private double protein;
    private double carbohydrates;
    private String imagePath;
    private String description;

    private Set<Type> types = new HashSet<>();

    /**
     * This constructor takes the main properties for an item
     * @param itemId
     * @param itemName
     * @param price
     * @throws IllegalArgumentException if an item with the provided id already exists
     */
    public AddItemTransaction( Long itemId, String itemName, double price ) {
        if ( Database.getItemById( itemId ) != null )
            throw new IllegalArgumentException( "An item with " + itemId + " already exists" );
        this.id = itemId;
        this.name = itemName;
        this.price = price;
    }

    @Override
    public void execute() {
        Item item = new Item( id, name, price, calories, fat, protein, carbohydrates, imagePath,
        description, new HashSet<>( types ));
        Database.addItem( item );
    }

    public AddItemTransaction withDescription( String description ) {
        this.description = description;
        return this;
    }

    public AddItemTransaction withType( Type type ) {
        types.add( type );
        return this;
    }

    public AddItemTransaction withImage( String path ) {
        imagePath = path;
        return this;
    }

    public AddItemTransaction withCalories( double calories ) {
        this.calories = calories;
        return this;
    }

    public AddItemTransaction withFat( double fat ) {
        this.fat = fat;
        return this;
    }

    public AddItemTransaction withProtein( double protein ) {
        this.protein = protein;
        return this;
    }

    public AddItemTransaction withCarbohydrates( double carbohydrates ) {
        this.carbohydrates = carbohydrates;
        return this;
    }
}
