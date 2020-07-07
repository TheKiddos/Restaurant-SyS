package org.thekiddos.manager.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Customer {
    @NonNull @Id
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
}
