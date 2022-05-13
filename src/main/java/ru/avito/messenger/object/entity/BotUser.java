package ru.avito.messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bot_user")
public class BotUser {

    @Id
    @Column(name = "bu_id")
    private Long userId;

    @Column(name = "bu_name")
    private String name;

}
