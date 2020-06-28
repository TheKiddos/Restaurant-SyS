package org.thekiddos.manager.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Customer {
    @NonNull
    private final Long Id;
    @NonNull
    private final String firstName;
    @NonNull
    private final String lastName;
}
