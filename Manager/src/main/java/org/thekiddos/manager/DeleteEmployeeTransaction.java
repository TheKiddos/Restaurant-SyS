package org.thekiddos.manager;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thekiddos.manager.repositories.Database;

public class DeleteEmployeeTransaction implements Transaction {
    private Long empId;

    public DeleteEmployeeTransaction( Long empId ) {
        this.empId = empId;
    }

    @Override
    public void execute() {
        Database.removeEmployeeById( empId );
    }
}
