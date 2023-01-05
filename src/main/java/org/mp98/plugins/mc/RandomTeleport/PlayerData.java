package org.mp98.plugins.mc.RandomTeleport;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class PlayerData {

    private Plugin plugin = null;
    private Player player = null;
    private String path = null;
    private File configFile = null;
    private FileConfiguration config = null;

    public PlayerData(Player player) {
        this.player = player;
        this.plugin = Bukkit.getPluginManager().getPlugin("RandomTeleport");
        this.path = plugin.getDataFolder().toString() + "playerData/";
    }

    private void getConfig() {
        if (player != null) {
            configFile = new File(path, player.getUniqueId().toString() + ".yml");
            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                plugin.saveResource((player.getUniqueId().toString() + ".yml"), false);
            }
        }
    }
}
