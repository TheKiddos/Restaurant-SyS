package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Service;

import java.util.ArrayList;
import java.util.List;

public abstract class AddServiceTransaction implements Transaction {
    protected Service service;
    private List<Long> items = new ArrayList<>();

    @Override
    public void execute() {
        for ( Long item : items )
            service.addItem( item );
        // TODO save to database
    }

    public void addItem( Long itemId ) {
        items.add( itemId );
    }
}
