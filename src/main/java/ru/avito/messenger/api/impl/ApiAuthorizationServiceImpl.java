package ru.avito.messenger.api.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.avito.messenger.api.ApiAuthorizationService;
import ru.avito.messenger.dao.AuthResponse;

@Service
public class ApiAuthorizationServiceImpl implements ApiAuthorizationService {
    @Value("${grant.type}")
    private String grantType;
    @Value("${client.id}")
    private String clientId;
    @Value("${client.secret}")
    private String clientSecret;
    @Value("${value.url}")
    private String apiUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<AuthResponse> getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("grant_type", grantType);
        request.add("client_id", clientId);
        request.add("client_secret", clientSecret);
        return restTemplate.exchange(apiUrl + "/token", HttpMethod.POST, new HttpEntity<>(request, headers), AuthResponse.class);
    }
}
