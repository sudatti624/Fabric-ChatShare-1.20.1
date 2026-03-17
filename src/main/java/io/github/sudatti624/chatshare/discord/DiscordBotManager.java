package io.github.sudatti624.chatshare.discord;

import io.github.sudatti624.chatshare.ChatShare;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.concurrent.TimeUnit;

/**
 * Discord Bot (JDA) のライフサイクルと接続状態を管理する。
 * JDAインスタンスとchannelIdはここで一元管理する。
 */
public class DiscordBotManager {

    private static JDA jda;
    private static long channelId;

    /**
     * JDAを初期化してリスナーを登録する。
     *
     * @param token     Botトークン
     * @param channelId 対象チャンネルID
     * @param listeners 登録するイベントリスナー
     */
    public static void initialize(String token, long channelId, ListenerAdapter... listeners)
            throws InterruptedException {
        DiscordBotManager.channelId = channelId;
        jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners((Object[]) listeners)
                .build();
        jda.awaitReady();
    }

    /**
     * JDAをシャットダウンする。
     * キュー内のメッセージ送信完了を最大5秒待ってから切断する。
     */
    public static void shutdown() {
        if (jda == null) return;
        try {
            jda.shutdown();
            if (!jda.awaitShutdown(5, TimeUnit.SECONDS)) {
                jda.shutdownNow();
                ChatShare.LOGGER.warn("JDA did not shut down gracefully, forced shutdown.");
            }
        } catch (InterruptedException e) {
            jda.shutdownNow();
            Thread.currentThread().interrupt();
        }
        jda = null;
    }

    public static JDA getJda() {
        return jda;
    }

    public static long getChannelId() {
        return channelId;
    }

    public static boolean isReady() {
        return jda != null && channelId > 0;
    }
}
