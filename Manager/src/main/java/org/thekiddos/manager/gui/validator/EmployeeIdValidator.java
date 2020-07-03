package org.thekiddos.manager.gui.validator;

import org.thekiddos.manager.repositories.Database;

public class EmployeeIdValidator extends IdValidator {
    @Override
    boolean isUnique( Long id ) {
        return !Database.getEmployeesId().contains( id );
    }
}
