package co.antiqu.repeatertickcounter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Diode;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public final class RepeaterTickCounter extends JavaPlugin implements Listener {

    private HashMap<ItemStack,Integer> lightningwands;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        lightningwands = new HashMap<>();
    }

    @Override
    public void onDisable() {
        lightningwands = null;
    }

    @EventHandler
    public void onBlockRightClick(PlayerInteractEvent evt) {
        Player player = evt.getPlayer();
        if(player.getItemInHand() == null) {
            return;
        }
        ItemStack item = player.getItemInHand();
        if(item.getType() != Material.BLAZE_ROD) {
            return;
        }
        if(evt.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Block block = evt.getClickedBlock();
        if(block.getType() != Material.DIODE_BLOCK_OFF) {
            return;
        }
        int delay = 0;
        Diode diode = (Diode) block.getState().getData();
        if(!lightningwands.containsKey(item)) {
            delay = diode.getDelay();
            lightningwands.put(item, delay);
        } else {
            delay = lightningwands.get(item) + diode.getDelay();
            lightningwands.put(item, delay);
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&bTotal Ticks&7] &bRT: &a"+delay+" &bGT: &a"+(delay*2)));
        evt.setCancelled(true);
    }

    @EventHandler
    public void onBlockLeftClick(PlayerInteractEvent evt) {
        Player player = evt.getPlayer();
        if(player.getItemInHand() == null) {
            return;
        }
        ItemStack item = player.getItemInHand();
        if(item.getType() != Material.BLAZE_ROD) {
            return;
        }
        if(evt.getAction() != Action.LEFT_CLICK_AIR) {
            return;
        }
        lightningwands.remove(item);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cTicks removed"));
    }

}
