package com.steaks4uce.Herobrine.listeners;
import com.steaks4uce.Herobrine.Herobrine;
import com.steaks4uce.Herobrine.logger.Logger;
import com.steaks4uce.Herobrine.skin.EntitySkin;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;   
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;

public class HeroEntity extends EntityListener {
    public static Herobrine plugin;
    Logger log = new Logger();
    
    public HeroEntity(Herobrine instance) {
        plugin = instance;
    }

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        Entity e = event.getEntity();
        if (e == plugin.hbEntity) {
            if (!(event.getCause()==DamageCause.ENTITY_ATTACK)) {
                event.setCancelled(true);
                e.setFireTicks(0);
            } else {
                event.setDamage(1); 
            }
        }
    }

    @Override
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        Entity e = event.getEntity();
        if (event.getCreatureType() == CreatureType.PIG_ZOMBIE && Herobrine.trackingEntity.booleanValue() == true && plugin.isDead() == true) {
            plugin.hbEntity = e;
            Herobrine.trackingEntity = Boolean.valueOf(false);
            PigZombie pz = (PigZombie)e;
            pz.setAngry(true);
            EntitySkin es = new EntitySkin();
            es.setSkin(pz, "http://www.nkrecklow.com/skins/herobrine.png");
        }
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        Entity e = event.getEntity();
        World w = event.getEntity().getWorld();
        if (e == plugin.hbEntity) {
            plugin.hbEntity = null;
            ItemStack appleDrop = new ItemStack(Material.APPLE, 1);
            w.dropItemNaturally(e.getLocation(), appleDrop);
            if (Herobrine.specialEffects.booleanValue() == true) {
                w.createExplosion(e.getLocation(), -1.0F);
            }
            Herobrine.isAttacking = false;
            event.setDroppedExp(0);
            event.getDrops().clear();
            if(e.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e.getLastDamageCause();
                if(nEvent.getDamager() instanceof Player) {
                    Player p = (Player)nEvent.getDamager();
                    if (Herobrine.sendMessages.booleanValue() == true) {
                        Random messages = new Random();
                        int message = messages.nextInt(3);
                        if (message == 1) {
                            p.sendMessage("<Herobrine> This isn't the end.");
                        } else if (message == 2) {
                            p.sendMessage("<Herobrine> I'll be back.");
                        } else if (message == 3) {
                            p.sendMessage("<Herobrine> I'm watching you, I always will be... ");
                        } else {
                            p.sendMessage("<Herobrine> I will prevail!");
                        }
                    } 
                    log.event(8, p.getName());
                }
            }
        }
    }

    @Override
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getTarget() == plugin.hbEntity) {
            event.setCancelled(true);
        }
    } 
}