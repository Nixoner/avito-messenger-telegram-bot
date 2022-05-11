package ru.avito.messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(targetEntity = BotUser.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private BotUser botUser;

    private String clientId;

    private String clientSecret;

}
