package ru.avito.messenger.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ApiSettingsId implements Serializable {
    @Column(name = "as_client_id")
    private String clientId;

    @Column(name = "as_client_Secret")
    private String clientSecret;

}
