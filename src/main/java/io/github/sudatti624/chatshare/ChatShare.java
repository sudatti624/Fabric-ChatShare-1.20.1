package io.github.sudatti624.chatshare;

import com.google.gson.Gson;
import io.github.sudatti624.chatshare.Config.ConfigGenerator;
import io.github.sudatti624.chatshare.Config.Config;
import io.github.sudatti624.chatshare.Message.ServerSendMessage;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ChatShare implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("chatshare");
    public static JDA jda;
    public static long channelId;

    @Override
    public void onInitialize() {
        try {
            // Config ファイルが存在しなければ生成
            ConfigGenerator.generateConfigIfNotExists();
            LOGGER.info("Config initialized successfully!");

            // Config ファイルを読み込む
            String configContent = Files.readString(Paths.get("config/chatshare/config.json"));
            Config config = new Gson().fromJson(configContent, Config.class);

            // Token の検証
            if (config.Token == null || config.Token.isEmpty()) {
                LOGGER.error("Token is empty! Please set the token in config.json");
                return;
            }

            // デフォルト値のままの場合は警告
            if (config.Token.equals("YOUR_BOT_TOKEN_HERE")) {
                LOGGER.warn("Token is not configured! Please set a valid Discord Bot token in config/chatshare/config.json");
                LOGGER.warn("Discord connection will be skipped until a valid token is provided.");
                return;
            }

            // ChannelID の検証
            if (config.ChannelID <= 0) {
                LOGGER.warn("ChannelID is not set! Please set a valid channelId in config/chatshare/config.json");
                LOGGER.warn("Discord messages will not be sent until ChannelID is configured.");
            } else {
                // グローバル変数に保存
                channelId = config.ChannelID;
                LOGGER.info("ChannelID set to: {}", channelId);
            }

            // Discord Bot の初期化
            initializeDiscordBot(config.Token);

            // チャットメッセージイベントリスナーの登録
            new ServerSendMessage().register();

        } catch (Exception e) {
            LOGGER.error("Failed to initialize ChatShare", e);
        }
    }

    private void initializeDiscordBot(String token) throws Exception {
        LOGGER.info("Initializing Discord Bot...");
        jda = JDABuilder.createDefault(token).build();
        jda.awaitReady();
        LOGGER.info("Discord Bot is ready! Bot ID: " + jda.getSelfUser().getId());
    }
}
