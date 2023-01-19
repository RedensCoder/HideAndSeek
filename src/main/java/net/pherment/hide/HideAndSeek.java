package net.pherment.hide;

import net.pherment.hide.arena.events.HiderDeath;
import net.pherment.hide.arena.events.killEvents;
import net.pherment.hide.commands.PlayerCMDs;
import net.pherment.hide.commands.PlayersTabCompleter;
import net.pherment.hide.files.ArenaConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HideAndSeek extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("[HideAndSeek] Plugin enabled!");

        ArenaConfig.setup();
        ArenaConfig.getArenaConfig().options().copyDefaults(true);
        if (ArenaConfig.getArenaConfig().getString("name") == null) {
            System.out.println("Has not arena!");
        } else {
            ArenaConfig.loadAllArenas();
        }
        ArenaConfig.save();

        getCommand("hideandseek").setExecutor(new PlayerCMDs());
        getCommand("hideandseek").setTabCompleter(new PlayersTabCompleter());

        Bukkit.getPluginManager().registerEvents(new HiderDeath(), this);
        Bukkit.getPluginManager().registerEvents(new killEvents(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("[HideAndSeek] Plugin disabled!");
    }
}
