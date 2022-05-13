package ru.avito.messenger.dao.pojo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Value {

    private Long id;

    private Images images;

    private String priceString;

    private Long statusId;

    private String title;

    private String url;

    private Long userId;

}
