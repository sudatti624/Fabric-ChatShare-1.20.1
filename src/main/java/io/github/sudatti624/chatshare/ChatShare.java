package io.github.sudatti624.chatshare;

import java.awt.Color;

import io.github.sudatti624.chatshare.Config.Config;
import io.github.sudatti624.chatshare.Config.ConfigLoader;
import io.github.sudatti624.chatshare.Message.DiscordMessageListener;
import io.github.sudatti624.chatshare.Message.PlayerJoinLeaveListener;
import io.github.sudatti624.chatshare.Message.ServerSendMessage;
import io.github.sudatti624.chatshare.Sender.SendToDiscord;
import io.github.sudatti624.chatshare.Sender.SendToMinecraft;
import io.github.sudatti624.chatshare.discord.DiscordBotManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MODのエントリポイント。
 * Config読み込み・Discord Bot初期化・各イベントリスナー登録の配線のみを担う。
 */
public class ChatShare implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("chatshare");

    @Override
    public void onInitialize() {
        try {
            Config config = ConfigLoader.load();
            LOGGER.info("Config loaded successfully!");

            if (!config.isTokenValid()) {
                LOGGER.warn("Token is not configured! Please set a valid token in config/chatshare/config.json");
                return;
            }

            if (!config.isChannelIdValid()) {
                LOGGER.warn("ChannelID is not set! Please set a valid channelId in config/chatshare/config.json");
                return;
            }

            // Discord Bot を初期化し、DiscordMessageListener を登録
            DiscordBotManager.initialize(config.getToken(), config.getChannelId(), new DiscordMessageListener());
            LOGGER.info("Discord Bot is ready! Bot ID: {}", DiscordBotManager.getJda().getSelfUser().getId());

            // Fabricイベントリスナーを登録
            new ServerSendMessage().register();
            new PlayerJoinLeaveListener().register();

            ServerLifecycleEvents.SERVER_STARTED.register(server -> {
                SendToMinecraft.setServer(server);
                SendToDiscord.sendEmbed("サーバーが起動しました！", null, Color.GREEN);
                LOGGER.info("Server started.");
            });

            ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
                SendToDiscord.sendEmbed("サーバーが停止しました！", null, Color.RED);
                DiscordBotManager.shutdown();
            });

        } catch (Exception e) {
            LOGGER.error("Failed to initialize ChatShare", e);
        }
    }
}
