package org.thekiddos.manager.gui.validator;

import org.thekiddos.manager.repositories.Database;

public class ItemIdValidator extends IdValidator {
    @Override
    boolean isUnique( Long id ) {
        return !Database.getItemsId().contains( id );
    }
}
