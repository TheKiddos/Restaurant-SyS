package org.thekiddos.manager.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Table {
    @NonNull
    private Long id;
    private int maxCapacity;

    public Table( Long id, int maxCapacity ) {
        this.id = id;
        this.maxCapacity = maxCapacity;
    }
}
