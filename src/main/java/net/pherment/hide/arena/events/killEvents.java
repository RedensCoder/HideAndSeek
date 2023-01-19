package net.pherment.hide.arena.events;

import net.pherment.hide.ArmsHandle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class killEvents implements Listener {
    @EventHandler
    public void onShot(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack seekStick = ArmsHandle.getStickFrom(event.getPlayer().getInventory().getItemInMainHand());
            if (seekStick != null) {
                ArmsHandle.seek(seekStick, event.getPlayer());
            }
        }
    }
}
