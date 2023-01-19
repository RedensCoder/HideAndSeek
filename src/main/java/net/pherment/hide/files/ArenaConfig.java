package net.pherment.hide.files;

import net.pherment.hide.arena.HASArena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ArenaConfig {
    private static File file;
    private static FileConfiguration arenaConfig;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("HideAndSeek").getDataFolder(), "ArenasConfig.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        arenaConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static void loadAllArenas() {
            HASArena arena = new HASArena(
                    Integer.parseInt(arenaConfig.getString("minPlayers")),
                    Integer.parseInt(arenaConfig.getString("maxPlayers")),
                    30,
                    arenaConfig.getString("name")
            );
            HASArena.arenasList.add(arena);
            arena.setLobbyLocation(arenaConfig.getLocation("lobby"));
            arena.setSeekersLocation(arenaConfig.getLocation("seekersSpawn"));
            arena.setHidersLocation(arenaConfig.getLocation("hidersSpawn"));
    }

    public static void save() {
        try {
            arenaConfig.save(file);
        } catch (IOException e) {
            System.out.println("File couldn't save!");
        }
    }

    public static void reload() {
        arenaConfig = YamlConfiguration.loadConfiguration(file);
    }

    /* ===== GETTERS & SETTERS ===== */

    public static FileConfiguration getArenaConfig() {
        return arenaConfig;
    }
}
