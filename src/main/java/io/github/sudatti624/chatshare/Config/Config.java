package io.github.sudatti624.chatshare.Config;

import com.google.gson.annotations.SerializedName;

/**
 * config.json のデータを保持するモデル。
 * バリデーションロジックも内包する。
 */
public class Config {

    private static final String DEFAULT_TOKEN = "YOUR_BOT_TOKEN_HERE";

    @SerializedName("Token")
    private String token;

    @SerializedName("ChannelID")
    private long channelId;

    public String getToken() {
        return token;
    }

    public long getChannelId() {
        return channelId;
    }

    public boolean isTokenValid() {
        return token != null && !token.isBlank() && !token.equals(DEFAULT_TOKEN);
    }

    public boolean isChannelIdValid() {
        return channelId > 0;
    }
}
