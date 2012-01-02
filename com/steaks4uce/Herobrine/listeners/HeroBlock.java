package com.steaks4uce.Herobrine.listeners;
import com.steaks4uce.Herobrine.Herobrine;
import com.steaks4uce.Herobrine.formats.SmokeArea;
import com.steaks4uce.Herobrine.PossibleActions;
import com.steaks4uce.Herobrine.text.CustomLogger;

import com.steaks4uce.Herobrine.text.TextGenerator;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockBreakEvent;

public class HeroBlock extends BlockListener {
    public static Herobrine plugin;
    CustomLogger log = new CustomLogger();

    public HeroBlock(Herobrine instance) {
        plugin = instance;
    }

    @Override
    public void onBlockIgnite(BlockIgniteEvent event) {
        Block b = event.getBlock();
        if (event.getCause().equals(BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL)) {
            Player p = event.getPlayer();
            World w = event.getBlock().getWorld();
            Block netherRack = b.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
            Block mossyCobble = b.getLocation().subtract(0.0D, 2.0D, 0.0D).getBlock();
            if (netherRack.getType().equals(Material.NETHERRACK) && mossyCobble.getType().equals(Material.MOSSY_COBBLESTONE) && plugin.isDead() && plugin.canSpawn(p.getWorld())) {
                Herobrine.isAttacking = true;
                if (Herobrine.changeEnvironment) {
                    w.setStorm(true);
                    w.setTime(14200); 
                }
                if (Herobrine.removeMossyCobblestone) {
                    mossyCobble.setType(Material.COBBLESTONE);
                }
                w.strikeLightning(b.getLocation());
                w.createExplosion(b.getLocation(), -1.0F);
                if (Herobrine.sendMessages) {
                    TextGenerator tg = new TextGenerator();
                    plugin.getServer().broadcastMessage(tg.getMessage());
                }
                if (p.getGameMode().equals(GameMode.CREATIVE)) {
                    p.setGameMode(GameMode.SURVIVAL);
                }
                Herobrine.trackingEntity = Boolean.valueOf(true);
                w.spawnCreature(b.getLocation(), CreatureType.ZOMBIE);
                Zombie z = (Zombie) plugin.hbEntity;
                z.setTarget(p);
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
            PossibleActions actions = new PossibleActions(plugin);
            actions.explodeChest(event.getPlayer(), b.getLocation());
        }
    }
}