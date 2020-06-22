package org.thekiddos.manager.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Customer {
    @NonNull
    private Long Id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
}
