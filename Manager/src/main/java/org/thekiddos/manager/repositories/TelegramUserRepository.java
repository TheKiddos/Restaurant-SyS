package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thekiddos.manager.models.TelegramUser;

import java.util.Optional;

public interface TelegramUserRepository extends CrudRepository<TelegramUser, Integer> {
    Optional<TelegramUser> findByEmail( String email );
}
