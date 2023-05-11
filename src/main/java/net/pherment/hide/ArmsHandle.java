package net.pherment.hide;

import net.pherment.hide.arena.HASArena;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;

public class ArmsHandle {
    public static void handle(Player player) {
        ItemStack seekStick = new ItemStack(Material.STICK);

        ItemMeta meta = seekStick.getItemMeta();

        meta.getPersistentDataContainer().set(NamespacedKey.fromString("seek_stick"), PersistentDataType.STRING, "SeekStick");
        meta.setDisplayName("Палка-Искалка");

        seekStick.setItemMeta(meta);

        player.getInventory().addItem(seekStick);
    }

    public static ItemStack getStickFrom(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();

        String name = meta.getPersistentDataContainer().get(NamespacedKey.fromString("seek_stick"), PersistentDataType.STRING);

        if (name == null) {
            return null;
        }

        return itemStack;
    }

    public static void seek(ItemStack itemStack, Player player) {
        RayTraceResult rayTraceBlocks = player.getWorld().rayTraceBlocks(
                player.getEyeLocation(),
                player.getLocation().getDirection(),
                200
        );
        RayTraceResult rayTraceEntity = player.getWorld().rayTraceEntities(
                player.getEyeLocation(),
                player.getLocation().getDirection(),
                200,
                e -> e instanceof Player && e != player
        );

        if (rayTraceBlocks == null) {
            return;
        }
        Block block = rayTraceBlocks.getHitBlock();
        if (block != null) {
            Player target = (Player) rayTraceEntity.getHitEntity();
            if (player.getLocation().distance(block.getLocation()) > player.getLocation().distance(target.getLocation())) {
                HASArena arena = HASArena.getArenaOfPlayer(player);
                arena.getGame().moveToSeekersTeam(target, arena);
            }
        }
    }
}
