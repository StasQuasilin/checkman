package ua.quasilin.chekmanszpt.entity;

/**
 * Created by szpt_user045 on 03.09.2019.
 */

public class ChannelSettings {
    private final String channelId;
    private final String channelName;

    public ChannelSettings(String channelId, String channelName) {
        this.channelId = channelId;
        this.channelName = channelName;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }
}
