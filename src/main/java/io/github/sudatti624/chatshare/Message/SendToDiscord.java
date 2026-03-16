package io.github.sudatti624.chatshare.Message;

import io.github.sudatti624.chatshare.ChatShare;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class SendToDiscord {
    public static void sendToDiscord(String message) {
        // JDAがまだ初期化されていない場合は無視
        if (ChatShare.jda == null) {
            return;
        }

        // ChannelIDが設定されていない場合は無視
        if (ChatShare.channelId == 0) {
            return;
        }

        try {
            TextChannel channel = ChatShare.jda.getTextChannelById(ChatShare.channelId);
            if (channel != null) {
                channel.sendMessage(message).queue();
                ChatShare.LOGGER.info("Message sent to Discord: {}", message);
            } else {
                ChatShare.LOGGER.warn("Channel not found: {}", ChatShare.channelId);
            }
        } catch (Exception e) {
            ChatShare.LOGGER.error("Failed to send message to Discord", e);
        }
    }
}
