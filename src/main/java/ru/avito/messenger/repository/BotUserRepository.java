package ru.avito.messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avito.messenger.entity.BotUser;

@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Long> {
}
