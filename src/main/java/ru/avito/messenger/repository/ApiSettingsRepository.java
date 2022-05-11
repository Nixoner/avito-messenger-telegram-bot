package ru.avito.messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avito.messenger.entity.ApiSettings;

@Repository
public interface ApiSettingsRepository extends JpaRepository<ApiSettings, Long> {
    ApiSettings findApiSettingsByBotUser_UserId(Long userId);
}
