package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thekiddos.manager.models.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
