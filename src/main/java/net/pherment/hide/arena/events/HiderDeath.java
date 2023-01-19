package net.pherment.hide.arena.events;

import net.pherment.hide.arena.HASArena;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class HiderDeath implements Listener {
    @EventHandler
    public void onDeath(EntityDamageEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            HASArena arena = HASArena.getArenaOfPlayer(player);

            if (arena != null) {
                if (event.getDamage() >= player.getHealth()) {
                    event.setCancelled(true);
                    arena.getGame().moveToSeekersTeam(player, arena);
                }
            }
        }
    }
}
