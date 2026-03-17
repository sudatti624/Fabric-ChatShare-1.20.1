package io.github.sudatti624.chatshare.Sender;

import java.awt.Color;

import io.github.sudatti624.chatshare.ChatShare;
import io.github.sudatti624.chatshare.discord.DiscordBotManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

/**
 * Discordチャンネルへのメッセージ・Embed送信を担当する。
 * チャンネル取得・送信処理のみを責務とし、整形は呼び出し元が行う。
 */
public class SendToDiscord {

    /** 設定済みチャンネルを取得する。未設定またはJDA未初期化の場合はnull */
    private static TextChannel getChannel() {
        if (!DiscordBotManager.isReady()) return null;
        TextChannel channel = DiscordBotManager.getJda().getTextChannelById(DiscordBotManager.getChannelId());
        if (channel == null) {
            ChatShare.LOGGER.warn("Channel not found: {}", DiscordBotManager.getChannelId());
        }
        return channel;
    }

    /** テキストメッセージを送信する */
    public static void sendMessage(String message) {
        TextChannel channel = getChannel();
        if (channel == null) return;
        try {
            channel.sendMessage(message).queue();
            ChatShare.LOGGER.info("Message sent to Discord: {}", message);
        } catch (Exception e) {
            ChatShare.LOGGER.error("Failed to send message to Discord", e);
        }
    }

    /** タイトル・説明・色でEmbedを送信する */
    public static void sendEmbed(String title, String description, Color color) {
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle(title)
                .setColor(color);
        if (description != null && !description.isBlank()) {
            builder.setDescription(description);
        }
        sendEmbed(builder);
    }

    /** 任意のEmbedBuilderで送信する */
    public static void sendEmbed(EmbedBuilder builder) {
        if (builder == null) return;
        TextChannel channel = getChannel();
        if (channel == null) return;
        try {
            channel.sendMessageEmbeds(builder.build()).queue();
            ChatShare.LOGGER.info("Embed sent to Discord");
        } catch (Exception e) {
            ChatShare.LOGGER.error("Failed to send embed to Discord", e);
        }
    }
}
