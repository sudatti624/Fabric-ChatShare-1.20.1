package io.github.sudatti624.chatshare.Message;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;

import static io.github.sudatti624.chatshare.Sender.SendToDiscord.sendMessage;

/**
 * Minecraftのチャットメッセージを受信し、Discordへ転送する。
 */
public class ServerSendMessage {
    public void register() {
        ServerMessageEvents.CHAT_MESSAGE.register((message, sender, server) -> {
            String playerName  = sender.getName().getString();
            String chatContent = message.getContent().getString();
            sendMessage("<" + playerName + "> " + chatContent);
        });
    }
}
