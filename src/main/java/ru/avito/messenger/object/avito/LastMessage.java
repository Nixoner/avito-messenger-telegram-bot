package ru.avito.messenger.dao.pojo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LastMessage {

    private Integer authorId;

    private Integer created;

    private String direction;

    private String id;

    private String type;

}
