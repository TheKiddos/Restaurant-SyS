package org.thekiddos.manager.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public final class OrderedItem {
    private final SimpleStringProperty itemName;
    private final SimpleIntegerProperty quantity;
    private final SimpleDoubleProperty unitPrice;

    public OrderedItem( String itemName, int quantity, double unitPrice ) {
        this.itemName = new SimpleStringProperty( itemName );
        this.quantity = new SimpleIntegerProperty( quantity );
        this.unitPrice = new SimpleDoubleProperty( unitPrice );
    }

    public String getItemName() {
        return itemName.get();
    }

    public void setItemName( String itemName ) {
        this.itemName.set( itemName );
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity( int quantity ) {
        this.quantity.set( quantity );
    }

    public double getUnitPrice() {
        return unitPrice.get();
    }

    public void setUnitPrice( double unitPrice ) {
        this.unitPrice.set( unitPrice );
    }
}
