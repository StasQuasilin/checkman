package ua.quasilin.chekmanszpt.entity;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 06.08.2019.
 */

public class SimpleChatMessage {
    private final int id;
    private final String text;

    private SimpleChatMessage(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public SimpleChatMessage(String text) {
        this(-1, text);
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
