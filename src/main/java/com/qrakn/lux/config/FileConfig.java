package com.qrakn.lux.config;

import com.qrakn.lux.Lux;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class FileConfig {

    private String fileName;
    private File configFile;
    private FileConfiguration config;

    public FileConfig(String fileName) {
        this.fileName = fileName;
        this.configFile = new File(Lux.getInstance().getDataFolder(), fileName);

        if (!this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();

            if (Lux.getInstance().getResource(fileName) == null) {
                try {
                    this.configFile.createNewFile();
                } catch (IOException e) {
                    Lux.getInstance().getLogger().severe("Failed to create new file " + fileName);
                }
            } else {
                Lux.getInstance().saveResource(fileName, false);
            }
        }

        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public FileConfig(File file, String fileName) {
        this.configFile = new File(file, fileName);

        if (!this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();

            if (Lux.getInstance().getResource(fileName) == null) {
                try {
                    this.configFile.createNewFile();
                } catch (IOException e) {
                    Lux.getInstance().getLogger().severe("Failed to create new file " + fileName);
                }
            } else {
                Lux.getInstance().saveResource(fileName, false);
            }
        }

        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public void save() {
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save config file " + this.configFile.toString());
            e.printStackTrace();
        }
    }
}
