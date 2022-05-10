package ru.avito.messenger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MessageHandler {
    @Value("${telegram.api.key}")
    private String token;

    @Bean
    public void getMessages() {
        TelegramBot bot = new TelegramBot(token);
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                String messageText = update.message().text();
                Long chatId = update.message().chat().id();

                if ("/start".equalsIgnoreCase(messageText)) {
                    Keyboard keyboard = new ReplyKeyboardMarkup(new KeyboardButton("Установить ключ")).oneTimeKeyboard(true);
                    bot.execute(new SendMessage(chatId,
                            "Приветствую! Чтобы зарегистрировать прослушивателя сообщений Авито, вам необходимо " +
                                    "отправить API-ключ Авито сообщением.").replyMarkup(keyboard));
                }
                if ("Установить ключ".equalsIgnoreCase(messageText)) {
                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                    map.add("grant_type", "client_credentials");
                    map.add("client_id", "Jw6EOCnNQZ2SJzN7dvth");
                    map.add("client_secret", "DIp0LXeHbRLIJCwW1E1nhJveZDLkGWV7ln_MknyH");
                    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);
                    ResponseEntity<String> response = restTemplate.exchange(
                            "https://api.avito.ru/token/",
                            HttpMethod.POST,
                            request,
                            String.class
                    );
                    ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

                    if(response.getStatusCode().is2xxSuccessful()) {
                        try {
                            AuthResponse authResponse = mapper.readValue(response.getBody(), AuthResponse.class);
                            bot.execute(new SendMessage(chatId, "Успешно! Ваш токен: " + authResponse
                                    .getAccessToken()));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
