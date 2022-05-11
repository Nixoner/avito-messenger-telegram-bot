package ru.avito.messenger.api;

import org.springframework.http.ResponseEntity;
import ru.avito.messenger.dao.ChatInfoResponse;
import ru.avito.messenger.dao.UserInfoResponse;

public interface ApiMessengerService {
    ResponseEntity<ChatInfoResponse> getChatsInfo(String token, UserInfoResponse userInfoResponse);
}
