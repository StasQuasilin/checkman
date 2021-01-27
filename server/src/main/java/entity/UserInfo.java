package entity;

import org.json.simple.JSONObject;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
public class UserInfo extends JsonAble{
    private final Client client;
    private final User user;
    private final String ip;
    private final String sessionId;

    public UserInfo(Client client, User user, String ip, String sessionId) {
        this.client = client;
        this.user = user;
        this.ip = ip;
        this.sessionId = sessionId;
    }

    public Client getClient() {
        return client;
    }

    public User getUser() {
        return user;
    }

    public String getIp() {
        return ip;
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean isValid(String ip){
        return this.ip.equals(ip);
    }


    @Override
    public JSONObject toJson() {
        final JSONObject json = user.toJson();
        json.put(IP, ip);
        json.put(CLIENT, client.toString());
        return json;
    }
}
