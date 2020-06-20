package org.thekiddos.manager;

import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.models.Type;
import org.thekiddos.manager.repositories.Database;

public class AddItemTransaction implements Transaction {
    private Item item;

    public AddItemTransaction( Long itemId, String itemName, double price ) {
        item = new Item( itemId, itemName, price );
    }

    @Override
    public void execute() {
        Database.addItem( item );
    }

    public AddItemTransaction withDescription( String description ) {
        item.setDescription( description );
        return this;
    }

    public AddItemTransaction withType( Type type ) {
        item.addType( type );
        return this;
    }

    public AddItemTransaction withImage( String path ) {
        item.setImagePath( path );
        return this;
    }

    public AddItemTransaction withCalories( double calories ) {
        item.setCalories( calories );
        return this;
    }

    public AddItemTransaction withFat( double fat ) {
        item.setFat( fat );
        return this;
    }

    public AddItemTransaction withProtein( double protein ) {
        item.setProtein( protein );
        return this;
    }

    public AddItemTransaction withCarbohydrates( double carbohydrates ) {
        item.setCarbohydrates( carbohydrates );
        return this;
    }
}
