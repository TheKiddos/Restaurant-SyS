package org.thekiddos.manager.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Customer {
    @NonNull
    private Long Id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
}
