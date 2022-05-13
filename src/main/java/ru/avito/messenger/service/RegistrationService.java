package ru.avito.messenger.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;

public interface BotRegistrationService {
    void registration(Long userId, String name, TelegramBot bot, Message message);
}
