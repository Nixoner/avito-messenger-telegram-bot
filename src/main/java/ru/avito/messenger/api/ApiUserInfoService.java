package ru.avito.messenger.api;

import org.springframework.http.ResponseEntity;
import ru.avito.messenger.dao.UserInfoResponse;

public interface ApiUserInfoService {
    ResponseEntity<UserInfoResponse> getUserInfo(String token);
}
