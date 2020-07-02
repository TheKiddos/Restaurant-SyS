package org.thekiddos.manager.gui.validator;

import org.thekiddos.manager.repositories.Database;

public class CustomerIdValidator extends IdValidator {
    @Override
    boolean isUnique( Long id ) {
        return !Database.getCustomers().contains( id );
    }
}
