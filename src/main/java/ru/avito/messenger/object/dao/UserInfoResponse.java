package ru.avito.messenger.dao;

import lombok.Data;

@Data
public class UserInfoResponse {

    private Integer id;

    private String email;

    private String name;

    private String phone;

    private String profileUrl;
}