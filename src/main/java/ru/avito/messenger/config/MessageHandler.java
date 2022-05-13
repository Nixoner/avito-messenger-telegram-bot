package ru.avito.messenger;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.avito.messenger.config.BotConfig;
import ru.avito.messenger.service.bot.BotRegistrationProcess;

@Configuration
public class MessageHandler {
    @Autowired
    private BotRegistrationProcess botRegistrationProcess;

    @Bean
    public void getMessages() {
        TelegramBot bot = new TelegramBot(BotConfig.token);
        bot.setUpdatesListener(updates -> {
            updates.forEach(update -> {
                Message message = getMessage(update);

                switch (message.text().toLowerCase()) {
                    case "/start":
                        botRegistrationProcess.registration(message);
                        break;
                }

            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private Message getMessage(Update update) {
        if (update.message() != null) {
            return update.message();
        }
        if (update.callbackQuery() != null) {
            return update.callbackQuery().message();
        }
        return null;
    }
}