package ru.avito.messenger;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import ru.avito.messenger.api.ApiAuthorizationService;
import ru.avito.messenger.api.ApiMessengerService;
import ru.avito.messenger.api.ApiUserInfoService;
import ru.avito.messenger.dao.AuthResponse;
import ru.avito.messenger.dao.ChatInfoResponse;
import ru.avito.messenger.dao.UserInfoResponse;

@Configuration
public class MessageHandler {
    @Value("${telegram.api.key}")
    private String token;
    @Autowired
    private ApiAuthorizationService apiAuthorizationService;
    @Autowired
    private ApiUserInfoService userInfoService;
    @Autowired
    private ApiMessengerService messengerService;

    @Bean
    public void getMessages() {
        TelegramBot bot = new TelegramBot(token);
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                String messageText = update.message().text();
                Long chatId = update.message().chat().id();

                if ("/start".equalsIgnoreCase(messageText)) {
                    Keyboard keyboard = new ReplyKeyboardMarkup(
                            new KeyboardButton("Установить ключ"),
                            new KeyboardButton("Получить сообщения")).oneTimeKeyboard(true);
                    bot.execute(new SendMessage(chatId,
                            "Приветствую! Чтобы зарегистрировать прослушивателя сообщений Авито, вам необходимо " +
                                    "отправить API-ключ Авито сообщением.").replyMarkup(keyboard));
                }
                if ("Установить ключ".equalsIgnoreCase(messageText)) {
                    ResponseEntity<AuthResponse> response = apiAuthorizationService.getToken();
                    if (response.getStatusCode().is2xxSuccessful()) {
                        bot.execute(new SendMessage(chatId, "Успешно! Ваш токен: "
                                + response.getBody().getAccessToken()));
                    }
                }
                if ("Получить сообщения".equalsIgnoreCase(messageText)) {
                    ResponseEntity<AuthResponse> response = apiAuthorizationService.getToken();
                    if (response.getStatusCode().is2xxSuccessful()) {
                        ResponseEntity<UserInfoResponse> userInfo = userInfoService.getUserInfo(response.getBody().getAccessToken());
                        ResponseEntity<ChatInfoResponse> chatsInfo = messengerService.getChatsInfo(response.getBody().getAccessToken(), userInfo.getBody());
                        bot.execute(new SendMessage(chatId, chatsInfo.getBody().getChats().get(0).getUpdated().toString()));
                    }
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
