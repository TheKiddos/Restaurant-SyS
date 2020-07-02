package org.thekiddos.manager.gui.validator;

import org.thekiddos.manager.repositories.Database;

public class TableIdValidator extends IdValidator {
    @Override
    boolean isUnique( Long id ) {
        return !Database.getTables().contains( id );
    }
}
