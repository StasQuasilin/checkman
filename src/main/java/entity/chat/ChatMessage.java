package entity.chat;

import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 26.07.2019.
 */
public class ChatMessage {
    private int id;
    private Timestamp timestamp;
    private Chat chat;
    private ChatMember sender;
    private String message;
}
