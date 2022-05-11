package ru.avito.messenger.dao.pojo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Chats {

    private Context context;

    private Integer created;

    private String id;

    private LastMessage lastMessage;

    private Integer updated;

    private List<User> users;
}
