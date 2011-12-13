package com.steaks4uce.Herobrine.listeners;
import com.steaks4uce.Herobrine.Herobrine;
import com.steaks4uce.Herobrine.effects.SmokeArea;
import com.steaks4uce.Herobrine.events.Events;
import com.steaks4uce.Herobrine.logger.Logger;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;
import java.util.Random;
import org.bukkit.event.block.BlockBreakEvent;

public class HeroBlock extends BlockListener {
    public static Herobrine plugin;
    Logger log = new Logger();

    public HeroBlock(Herobrine instance) {
        plugin = instance;
    }

    @Override
    public void onBlockIgnite(BlockIgniteEvent event) {
        Block b = event.getBlock();
        if (event.getCause() == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            Player p = event.getPlayer();
            World w = event.getBlock().getWorld();
            Block netherRack = b.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
            Block mossyCobble = b.getLocation().subtract(0.0D, 2.0D, 0.0D).getBlock();
            if (netherRack.getType().equals(Material.NETHERRACK) && mossyCobble.getType().equals(Material.MOSSY_COBBLESTONE) && plugin.isDead() == true && plugin.canSpawn(p.getWorld())) {
                Herobrine.isAttacking = true;
                if (Herobrine.changeEnvironment.booleanValue() == true) {
                    w.setStorm(true);
                    w.setTime(14200); 
                }
                if (Herobrine.removeMossyCobblestone.booleanValue() == true) {
                    mossyCobble.setType(Material.COBBLESTONE);
                }
                if (Herobrine.specialEffects.booleanValue() == true) {
                    w.strikeLightning(b.getLocation());
                    w.createExplosion(b.getLocation(), -1.0F);
                }
                if (Herobrine.sendMessages.booleanValue() == true) {
                    Random messages = new Random();
                    int message = messages.nextInt(3);
                    if (message == 0) {
                        plugin.getServer().broadcastMessage("<Herobrine> Only god can help you now!");
                    } else if (message == 1) {
                        plugin.getServer().broadcastMessage("<Herobrine> Prepare for hell!");
                    } else {
                        plugin.getServer().broadcastMessage("<Herobrine> Never doubt my power!");
                    }
                }
                Herobrine.trackingEntity = Boolean.valueOf(true);
                w.spawnCreature(b.getLocation(), CreatureType.PIG_ZOMBIE);
                log.event(1, p.getName()); 
            }
        }
    }
    
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        Block b = event.getBlock();
        SmokeArea sa = null;
        for (SmokeArea smoke : plugin.smokes) {
            if (smoke.loc.equals(b.getLocation())) {
                sa = smoke;
            }
        }
        if (sa != null) {
            plugin.smokes.remove(sa);
        }
        if (b.getType().equals(Material.LOCKED_CHEST)) {
            event.setCancelled(true);
            b.setType(Material.AIR);
            Events actions = new Events(plugin);
            actions.explodeChest(event.getPlayer(), b.getLocation());
        }
    }
}