package ru.avito.messenger.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private Date sendDate;

    @OneToOne(targetEntity = BotUser.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", nullable = false)
    private BotUser botUser;

}
