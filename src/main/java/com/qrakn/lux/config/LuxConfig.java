package com.qrakn.lux.config;

import com.qrakn.lux.util.MessageUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum LuxConfig {

    ITEMS(new FileConfig(("items.yml"))),
    MESSAGES(new FileConfig("messages.yml")),
    ARENAS(new FileConfig("arenas.yml"));

    private final FileConfig config;

    public FileConfiguration getFileConfiguration() { return config.getConfig(); }

    public String getString(String path) {
        return config.getConfig().getString(path);
    }

    public String getColoredString(String path) {
        return MessageUtils.color(getString(path));
    }

    public void set(String path, Object value) {
        config.getConfig().set(path, value);
    }

    public static void save() {
        Stream.of(LuxConfig.ITEMS, LuxConfig.ARENAS, LuxConfig.MESSAGES)
                .map(LuxConfig::getConfig)
                .forEach(FileConfig::save);
    }
}
