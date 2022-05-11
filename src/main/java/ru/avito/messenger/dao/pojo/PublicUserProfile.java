package ru.avito.messenger.dao.pojo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PublicUserProfile {

    private Avatar avatar;

    private Long itemId;

    private String url;

    private Long userId;

}
