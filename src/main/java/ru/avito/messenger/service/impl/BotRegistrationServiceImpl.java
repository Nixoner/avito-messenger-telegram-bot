package ru.avito.messenger.service.impl;

import org.springframework.stereotype.Service;
import ru.avito.messenger.entity.BotUser;
import ru.avito.messenger.repository.BotUserRepository;
import ru.avito.messenger.service.BotRegistrationService;

@Service
public class BotRegistrationServiceImpl implements BotRegistrationService {
    private final BotUserRepository botUserRepository;

    BotRegistrationServiceImpl(BotUserRepository botUserRepository) {
        this.botUserRepository = botUserRepository;
    }

    @Override
    public void registration(Long userId,
                             String clientId,
                             String clientSecret) {
        botUserRepository.save(
                BotUser.builder()
                        .userId(userId)
                        .name("Rizvan")
                        .build());
    }
}
