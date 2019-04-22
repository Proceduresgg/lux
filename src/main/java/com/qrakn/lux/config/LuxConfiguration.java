package com.qrakn.lux.config;

import lombok.Getter;

@Getter
public class LuxConfiguration {

    @Getter private static FileConfig messages = new FileConfig("messages.yml");
}
