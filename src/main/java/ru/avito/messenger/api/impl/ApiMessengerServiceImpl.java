package ru.avito.messenger.api.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.avito.messenger.api.ApiMessengerService;
import ru.avito.messenger.dao.ChatInfoResponse;
import ru.avito.messenger.dao.UserInfoResponse;
@Service
public class ApiMessengerServiceImpl implements ApiMessengerService {
    @Value("${value.url}")
    private String apiUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<ChatInfoResponse> getChatsInfo(String token, UserInfoResponse userInfoResponse) {
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(token);
        return restTemplate.exchange(
                apiUrl + "/messenger/v1/accounts/" + userInfoResponse.getId() + "/chats",
                HttpMethod.GET,
                new HttpEntity<>(header),
                ChatInfoResponse.class);
    }
}
