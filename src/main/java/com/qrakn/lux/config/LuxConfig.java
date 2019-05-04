package com.qrakn.lux.config;

import com.qrakn.lux.util.MessageUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;

@RequiredArgsConstructor
public enum LuxConfig {

    ITEMS(new FileConfig(("items.yml"))),
    MESSAGES(new FileConfig("messages.yml"));

    private final FileConfig config;

    public FileConfiguration getConfig() { return config.getConfig(); }

    public String getString(String path) {
        return config.getConfig().getString(path);
    }

    public String getColoredString(String path) {
        return MessageUtils.color(getString(path));
    }
}
