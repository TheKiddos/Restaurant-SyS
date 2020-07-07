package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thekiddos.manager.models.Customer;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByFirstName( String firstName );
    Optional<Customer> findByLastName( String lastName );
}
