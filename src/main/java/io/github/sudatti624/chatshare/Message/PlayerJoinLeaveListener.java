package io.github.sudatti624.chatshare.Message;

import java.awt.Color;

import io.github.sudatti624.chatshare.Sender.SendToDiscord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

/**
 * プレイヤーの参加・退出イベントを受信し、DiscordへEmbed通知を送信する。
 */
public class PlayerJoinLeaveListener {
    public void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            String name = handler.getPlayer().getName().getString();
            SendToDiscord.sendEmbed(new EmbedBuilder()
                    .setAuthor(name + " が参加しました", null, "https://cravatar.eu/avatar/" + name)
                    .setColor(Color.GREEN));
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            String name = handler.getPlayer().getName().getString();
            SendToDiscord.sendEmbed(new EmbedBuilder()
                    .setAuthor(name + " が退出しました", null, "https://cravatar.eu/avatar/" + name)
                    .setColor(Color.GRAY));
        });
    }
}
