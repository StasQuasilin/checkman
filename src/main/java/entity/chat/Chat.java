package entity.chat;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by szpt_user045 on 26.07.2019.
 */
public class Chat {
    private int id;
    private String title;
    private Set<ChatMember> members = new HashSet<>();
}
