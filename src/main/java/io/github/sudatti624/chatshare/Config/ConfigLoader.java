package io.github.sudatti624.chatshare.Config;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configファイルの生成と読み込みを担当する。
 * ファイルが存在しなければデフォルト値で生成し、存在すれば読み込む。
 */
public class ConfigLoader {

    private static final String CONFIG_DIR  = "config/chatshare";
    private static final String CONFIG_FILE = CONFIG_DIR + "/config.json";

    /** Configファイルを生成（なければ）してロードする */
    public static Config load() throws IOException {
        Path dir  = Paths.get(CONFIG_DIR);
        Path file = Paths.get(CONFIG_FILE);

        Files.createDirectories(dir);

        if (!Files.exists(file)) {
            Files.writeString(file, defaultConfig());
        }

        return new Gson().fromJson(Files.readString(file), Config.class);
    }

    private static String defaultConfig() {
        return """
                {
                  "Token": "YOUR_BOT_TOKEN_HERE",
                  "ChannelID": 0
                }""";
    }
}

