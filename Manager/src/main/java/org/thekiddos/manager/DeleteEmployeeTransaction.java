package org.thekiddos.manager;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thekiddos.manager.repositories.Database;

@Data
@RequiredArgsConstructor
public class DeleteEmployeeTransaction implements Transaction {
    @NonNull
    private Long empId;

    @Override
    public void execute() {
        Database.removeEmployeeById( empId );
    }
}
