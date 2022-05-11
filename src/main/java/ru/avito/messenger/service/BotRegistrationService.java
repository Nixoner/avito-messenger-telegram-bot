package ru.avito.messenger.service;

public interface BotRegistrationService {
    void registration(Long userId, String clientId, String clientSecret);
}
