package net.pherment.hide.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class PlayersTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            if (args.length == 1) {
                return List.of(
                        "join",
                        "leave",
                        "arena"
                );
            } else if (args[0].equals("arena") && args.length == 2) {
                return List.of(
                        "create",
                        "setLobby",
                        "setSeekersSpawn",
                        "setHidersSpawn"
                );
            }
        } else {
            if (args.length == 1) {
                return List.of(
                        "join",
                        "leave"
                );
            }
        }


        return null;
    }
}
