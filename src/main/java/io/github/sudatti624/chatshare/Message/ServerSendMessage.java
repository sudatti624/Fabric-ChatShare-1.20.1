package io.github.sudatti624.chatshare.Message;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;

import static io.github.sudatti624.chatshare.Message.SendToDiscord.sendToDiscord;

public class ServerSendMessage {
    public void register() {
        ServerMessageEvents.CHAT_MESSAGE.register((message, sender, server) -> {
            String chatContent = message.getContent().getString();
            String playerName = sender.getName().getString();
            String messageContent = "<" + playerName + ">: " + chatContent;

            sendToDiscord(messageContent);
        });
    }
}
