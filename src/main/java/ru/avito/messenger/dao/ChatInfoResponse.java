package ru.avito.messenger.dao;

import lombok.Data;
import ru.avito.messenger.dao.pojo.Chats;

import java.util.List;
@Data
public class ChatInfoResponse {

    List<Chats> chats;

}
