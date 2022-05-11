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
import ru.avito.messenger.entity.ApiSettings;
import ru.avito.messenger.entity.BotUser;
import ru.avito.messenger.entity.ChatHistory;
import ru.avito.messenger.repository.ApiSettingsRepository;
import ru.avito.messenger.repository.BotUserRepository;
import ru.avito.messenger.repository.ChatHistoryRepository;
import ru.avito.messenger.service.BotRegistrationService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private BotRegistrationService botRegistrationService;
    @Autowired
    private ChatHistoryRepository chatHistoryRepository;
    @Autowired
    private ApiSettingsRepository apiSettingsRepository;
    @Autowired
    private BotUserRepository botUserRepository;

    @Bean
    public void getMessages() {
        TelegramBot bot = new TelegramBot(token);
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                String messageText = update.message().text();
                Long chatId = update.message().chat().id();
                Long userId = update.message().from().id();

                if ("/start".equalsIgnoreCase(messageText)) {
                    botRegistrationService.registration(
                            update.message().from().id(),
                            update.message().from().firstName()
                    );
                    Keyboard keyboard = new ReplyKeyboardMarkup(
                            new KeyboardButton("Установить")).oneTimeKeyboard(true);
                    bot.execute(new SendMessage(chatId,
                            "Приветствую! Чтобы зарегистрировать прослушивателя сообщений Авито, вам необходимо " +
                                    "установить client_id и client_secret из личного кабинета Авито.")
                            .replyMarkup(keyboard));
                }
                Optional<BotUser> user = botUserRepository.findById(userId);
                user.ifPresent(botUser -> chatHistoryRepository.save(
                        ChatHistory.builder()
                                .botUser(botUser)
                                .text(messageText)
                                .sendDate(Date.valueOf(LocalDate.now()))
                                .build()
                ));
                if ("Установить".equalsIgnoreCase(messageText)) {
                    bot.execute(new SendMessage(chatId, "Введите client_id и client_secret через запятую: (в формате: id,secret)"));
                }
                List<ChatHistory> histories = chatHistoryRepository.findChatHistoryByBotUser_UserId(userId);
                if (histories.size() > 1 && "Установить".equalsIgnoreCase(histories.get(histories.size() - 1).getText())) {
                    String[] apiSettings = messageText.split(",");
                    apiSettingsRepository
                            .save(ApiSettings.builder()
                                    .clientId(apiSettings[0])
                                    .clientSecret(apiSettings[1])
                                    .botUser(BotUser.builder()
                                            .userId(userId)
                                            .name(update.message().from().firstName())
                                            .build())
                                    .build()
                            );
                    Keyboard keyboard = new ReplyKeyboardMarkup(
                            new KeyboardButton("Получить токен")).oneTimeKeyboard(true);
                    bot.execute(new SendMessage(chatId, "Данные успешно сохранены.").replyMarkup(keyboard));
                }
                if ("Получить токен".equalsIgnoreCase(messageText)) {
                    ResponseEntity<AuthResponse> token = apiAuthorizationService.getToken(userId);
                    bot.execute(new SendMessage(chatId, "Ваш токен: " + token.getBody().getAccessToken()));
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
