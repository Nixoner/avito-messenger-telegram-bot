package ru.avito.messenger.api.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.avito.messenger.api.ApiUserInfoService;
import ru.avito.messenger.dao.UserInfoResponse;

@Service
public class ApiUserInfoServiceImpl implements ApiUserInfoService {
    @Value("${value.url}")
    private String apiUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<UserInfoResponse> getUserInfo(String token) {
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(token);
        return restTemplate.exchange(apiUrl + "/core/v1/accounts/self", HttpMethod.GET, new HttpEntity<>(header), UserInfoResponse.class);
    }
}
