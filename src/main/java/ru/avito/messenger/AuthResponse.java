package ru.avito.messenger;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String expiresIn;
    private String tokenType;
}
