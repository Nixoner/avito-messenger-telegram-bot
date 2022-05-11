package ru.avito.messenger.api;

import org.springframework.http.ResponseEntity;
import ru.avito.messenger.dao.AuthResponse;

public interface ApiAuthorizationService {
    ResponseEntity<AuthResponse> getToken();
}
