package ru.avito.messenger.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.avito.messenger.entity.BotUser;
import ru.avito.messenger.repository.BotUserRepository;
import ru.avito.messenger.service.BotRegistrationService;

@Service
public class BotRegistrationServiceImpl implements BotRegistrationService {
    private final BotUserRepository botUserRepository;

    BotRegistrationServiceImpl(BotUserRepository botUserRepository) {
        this.botUserRepository = botUserRepository;
    }

    @Override
    public void registration(Long userId,
                             String name,
                             TelegramBot bot, Message message) {
        Keyboard keyboard = new InlineKeyboardMarkup(
                new InlineKeyboardButton("Установить")
                        .callbackData("setApiSettings")
        );
        bot.execute(new SendMessage(message.chat().id(),
                "Приветствую! Чтобы зарегистрировать прослушивателя сообщений Авито, вам необходимо " +
                        "установить client_id и client_secret из личного кабинета Авито.")
                .replyMarkup(keyboard));

        botUserRepository.save(
                BotUser.builder()
                        .userId(userId)
                        .name(name)
                        .build());
    }
}
