package ru.avito.messenger.dao.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class Main {

    @JsonProperty("140x105")
    private String imgLink;

}
