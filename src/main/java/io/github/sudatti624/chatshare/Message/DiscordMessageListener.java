package io.github.sudatti624.chatshare.Message;

import io.github.sudatti624.chatshare.discord.DiscordBotManager;
import io.github.sudatti624.chatshare.Sender.SendToMinecraft;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

/**
 * Discordからのメッセージを受信し、整形してMinecraftへ転送する。
 */
public class DiscordMessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!DiscordBotManager.isReady()) return;
        if (event.getChannel().getIdLong() != DiscordBotManager.getChannelId()) return;

        String name    = event.getAuthor().getName();
        String content = event.getMessage().getContentRaw();

        // 空メッセージ（画像のみ等）は無視
        if (content.isBlank()) return;

        // [Discord] を緑色にして表示
        Text message = Text.literal("[Discord] ").formatted(Formatting.GREEN)
                .append(Text.literal("<" + name + "> " + content).formatted(Formatting.WHITE));

        SendToMinecraft.broadcast(message);
    }
}
