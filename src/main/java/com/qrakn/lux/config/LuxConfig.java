package com.qrakn.lux.config;

import com.qrakn.lux.util.MessageUtils;
import lombok.Getter;

@Getter
public enum LuxConfig {

    INSTANCE;

    private final FileConfig messages = new FileConfig("messages.yml");
    private final FileConfig items = new FileConfig("items.yml");

    public String getMessage(String path) {
        return messages.getConfig().getString(path);
    }

    public String getColoredMessage(String path) {
        return MessageUtils.color(getMessage(path));
    }

    public String getColor(String path) {
        return items.getConfig().getString(path);
    }
}
