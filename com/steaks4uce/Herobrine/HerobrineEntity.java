/*    */ package com.steaks4uce.Herobrine;
/*    */ 
         import java.util.Random;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.CreatureType;
/*    */ import org.bukkit.entity.Entity;
         import org.bukkit.entity.LivingEntity;
         import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
         import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
         import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*    */ import org.bukkit.event.entity.EntityDeathEvent;
/*    */ import org.bukkit.event.entity.EntityListener;
         import org.bukkit.event.entity.EntityTargetEvent;
/*    */ import org.bukkit.inventory.ItemStack;
         import org.getspout.spoutapi.SpoutManager;
         import org.getspout.spoutapi.player.EntitySkinType;
         import org.getspout.spoutapi.player.SpoutPlayer;
/*    */ 
/*    */ public class HerobrineEntity extends EntityListener
/*    */ {
/*    */   public static Herobrine plugin;
/*    */   public static int i;
/*    */ 
/*    */   public HerobrineEntity(Herobrine instance) {
/* 20 */     plugin = instance;
/*    */   }
/*    */ 
/*    */   public void onEntityDamage(EntityDamageEvent event) {
/* 25 */     Entity e = event.getEntity();
/* 26 */     if (e == plugin.herobrineModel) {
/* 27 */        if (!(event.getCause()==DamageCause.ENTITY_ATTACK)) {
/* 28 */            event.setCancelled(true);
/* 29 */            e.setFireTicks(0);
/*    */        } else {
                    event.setDamage(2); 
                }
/*    */     }
           }
/*    */ 
/*    */   public void onCreatureSpawn(CreatureSpawnEvent event) {
/* 37 */     Entity e = event.getEntity();
/* 38 */     if ((event.getCreatureType() == CreatureType.ZOMBIE) && (Herobrine.trackingEntity.booleanValue() == true) && 
/* 39 */       (plugin.isDead()==true)) {
/* 40 */        plugin.herobrineModel = e;
/* 41 */        Herobrine.trackingEntity = Boolean.valueOf(false);
                for (Player p : e.getWorld().getPlayers()) {
                    SpoutPlayer sp = SpoutManager.getPlayer(p);
                    if (sp.isSpoutCraftEnabled()) {
                        LivingEntity le = (LivingEntity)plugin.herobrineModel;
                        SpoutManager.getAppearanceManager().setEntitySkin(sp, le, "http://www.nkrecklow.com/herobrine.png", EntitySkinType.DEFAULT);
                    }
                }
             }
/*    */   }
/*    */ 
/*    */   public void onEntityDeath(EntityDeathEvent event) {
/* 48 */     Entity e = event.getEntity();
/* 49 */     World w = event.getEntity().getWorld();
/* 50 */     if (e == plugin.herobrineModel) {
/* 51 */       plugin.herobrineModel = null;
/* 55 */       ItemStack appleDrop = new ItemStack(Material.APPLE, 1);
/* 56 */       w.dropItemNaturally(e.getLocation(), appleDrop);
/* 57 */       if (Herobrine.specialEffects.booleanValue() == true) {
/* 58 */         w.createExplosion(e.getLocation(), -1.0F);
/*    */       }
/* 66 */       event.setDroppedExp(0);
/* 67 */       event.getDrops().clear();
               if(e.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e.getLastDamageCause();
                    if(nEvent.getDamager() instanceof Player) {
                         Player p = (Player)nEvent.getDamager();
/* 52 */                 if (Herobrine.sendMessages.booleanValue() == true) {
                            Random messages = new Random();
                            int message = messages.nextInt(3);
                            if (message == 1) {
/* 53 */                        p.sendMessage("<Herobrine> This isn't the end.");
                            } else if (message == 2) {
                                p.sendMessage("<Herobrine> I'll be back.");
                            } else if (message == 3) {
                                p.sendMessage("<Herobrine> I'm still alive...");
                            } else {
                                p.sendMessage("<Herobrine> I will prevail!");
                            }
/*    */                 } 
                         plugin.logEvent(8, p.getName());
                    }
                }
/*    */     }
/*    */   }

           public void onEntityTarget(EntityTargetEvent event) {
               if (event.getTarget() == plugin.herobrineModel) {
                   event.setCancelled(true);
               }
           } 
/*    */ }