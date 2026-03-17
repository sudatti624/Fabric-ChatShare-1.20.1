package io.github.sudatti624.chatshare.Sender;

import io.github.sudatti624.chatshare.ChatShare;
import net.minecraft.text.Text;
import net.minecraft.server.MinecraftServer;

/**
 * Minecraftサーバーへのメッセージブロードキャストを担当する。
 * 整形済みTextを受け取り、全プレイヤーに配信する。
 */
public class SendToMinecraft {

    private static MinecraftServer server;

    public static void setServer(MinecraftServer minecraftServer) {
        server = minecraftServer;
    }

    /** 整形済みTextを全プレイヤーへブロードキャストする */
    public static void broadcast(Text message) {
        if (server == null) {
            ChatShare.LOGGER.warn("MinecraftServer is not initialized yet");
            return;
        }
        try {
            server.getPlayerManager().broadcast(message, false);
            ChatShare.LOGGER.info("Message broadcast to Minecraft: {}", message.getString());
        } catch (Exception e) {
            ChatShare.LOGGER.error("Failed to broadcast message to Minecraft", e);
        }
    }
}
