package ru.avito.messenger.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
import ru.avito.messenger.entity.ApiSettings;
import ru.avito.messenger.repository.ApiSettingsRepository;

@Service
public class ApiAuthorizationServiceImpl implements ApiAuthorizationService {
    @Value("${grant.type}")
    private String grantType;
    @Value("${value.url}")
    private String apiUrl;
    @Autowired
    private ApiSettingsRepository apiSettingsRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<AuthResponse> getToken(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        ApiSettings apiSettings = apiSettingsRepository.findApiSettingsByBotUser_UserId(userId);
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("grant_type", grantType);
        request.add("client_id", apiSettings.getClientId());
        request.add("client_secret", apiSettings.getClientSecret());
        return restTemplate.exchange(apiUrl + "/token", HttpMethod.POST, new HttpEntity<>(request, headers), AuthResponse.class);
    }
}
