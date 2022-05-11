package ru.avito.messenger.dao.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Avatar {

    @JsonProperty("default")
    private String defaultImage;

    @JsonProperty("images")
    private Map<String, String> images;

}
