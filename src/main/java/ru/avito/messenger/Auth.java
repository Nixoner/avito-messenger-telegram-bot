package ru.avito.messenger;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import org.yaml.snakeyaml.introspector.Property;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Auth {
    private String clientId;
    private String clientSecret;
    private String grantType;
}
