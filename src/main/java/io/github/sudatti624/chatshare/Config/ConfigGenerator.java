package io.github.sudatti624.chatshare.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigGenerator {
    private static final String CONFIG_DIR = "config/chatshare";
    private static final String CONFIG_FILE = CONFIG_DIR + "/config.json";

    public static void generateConfigIfNotExists() throws IOException {
        var configPath = Paths.get(CONFIG_FILE);
        
        // フォルダが存在しなければ作成
        Files.createDirectories(Paths.get(CONFIG_DIR));
        
        // ファイルが存在しなければ作成
        if (!Files.exists(configPath)) {
            Files.writeString(configPath, getDefaultConfig());
        }
    }

    private static String getDefaultConfig() {
        return """
                {
                  "Token": "YOUR_BOT_TOKEN_HERE",
                  "ChannelID": 0
                }""";
    }
}
