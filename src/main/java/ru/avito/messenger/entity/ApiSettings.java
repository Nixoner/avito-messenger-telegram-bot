package ru.avito.messenger.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity
public class ApiSettings {

    @Id
    private Long id;

    @OneToOne(targetEntity = BotUser.class)
    private Long userId;

    private String clientId;

    private String clientSecret;

}
