package org.mp98.plugins.mc.RandomTeleport;


import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;
import java.util.logging.Logger;
import java.util.random.RandomGenerator;

public class RandomTeleport extends JavaPlugin implements Listener, CommandExecutor {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    private double maxX = 5000;
    private double maxZ = 5000;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            teleportRandom(event.getPlayer());
        }
    }

    private void creteConfig() {
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
    }

    public void teleportRandom(Player player) {
        World world = player.getWorld();

        Location loc = getRandomPosition(player.getLocation());

        while (world.getHighestBlockAt(loc).getType() == Material.WATER) {
            loc = getRandomPosition(loc);
        }

        loc.setY(loc.getY() + 1);

        player.teleport(loc);
    }

    public Location getRandomPosition(Location cLoc) {
        Random rand = new Random();
        double randX = rand.nextDouble(this.maxX);
        double randZ = rand.nextDouble(this.maxZ);

        randX = (rand.nextBoolean()?randX:(randX*-1));
        randZ = (rand.nextBoolean()?randZ:(randZ*-1));

        cLoc.setX(randX);
        cLoc.setZ(randZ);

        World world = cLoc.getWorld();
        cLoc.setY(world.getHighestBlockAt(cLoc).getY());

        return cLoc;
    }
}
