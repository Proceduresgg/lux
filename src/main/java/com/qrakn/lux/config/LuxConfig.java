package com.qrakn.lux.config;

import com.qrakn.lux.util.MessageUtils;
import lombok.Getter;

@Getter
public class LuxConfig {

    @Getter private static FileConfig messages = new FileConfig("messages.yml");
    @Getter private static FileConfig items = new FileConfig("items.yml");

    public static String getMessage(String path) {
        return messages.getConfig().getString(path);
    }

    public static String getColoredMessage(String path) {
        return MessageUtils.color(getMessage(path));
    }

    public static String getColor(String path) {
        return items.getConfig().getString(path);
    }
}
