/*    */ package com.steaks4uce.Herobrine;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.CreatureType;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ import org.bukkit.event.entity.EntityDeathEvent;
/*    */ import org.bukkit.event.entity.EntityListener;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class HerobrineEntity extends EntityListener
/*    */ {
/*    */   public static Herobrine plugin;
/*    */   public static int i;
/*    */ 
/*    */   public HerobrineEntity(Herobrine instance)
/*    */   {
/* 20 */     plugin = instance;
/*    */   }
/*    */ 
/*    */   public void onEntityDamage(EntityDamageEvent event)
/*    */   {
/* 25 */     Entity e = event.getEntity();
/* 26 */     if (e == plugin.herobrineModel) {
/* 27 */       if ((event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) || (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) || (event.getCause() == EntityDamageEvent.DamageCause.FIRE) || (event.getCause()== EntityDamageEvent.DamageCause.FALL)) {
/* 28 */         event.setCancelled(true);
/* 29 */         e.setFireTicks(0);
/*    */       }
               event.setDamage(1); 
/*    */     }
           }
/*    */ 
/*    */   public void onCreatureSpawn(CreatureSpawnEvent event)
/*    */   {
/* 37 */     Entity e = event.getEntity();
/* 38 */     if ((event.getCreatureType() == CreatureType.ZOMBIE) && (Herobrine.trackingEntity.booleanValue() == true) && 
/* 39 */       (plugin.herobrineModel == null)) {
/* 40 */       plugin.herobrineModel = e;
/* 41 */       Herobrine.trackingEntity = Boolean.valueOf(false);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void onEntityDeath(EntityDeathEvent event)
/*    */   {
/* 48 */     Entity e = event.getEntity();
/* 49 */     World w = event.getEntity().getWorld();
/* 50 */     if (e == plugin.herobrineModel) {
/* 51 */       plugin.herobrineModel = null;
/* 52 */       if (Herobrine.sendMessages.booleanValue() == true) {
/* 53 */         plugin.getServer().broadcastMessage("<Herobrine> This isn't the end.");
/*    */       }
/* 55 */       ItemStack appleDrop = new ItemStack(Material.GOLDEN_APPLE, 1);
/* 56 */       w.dropItemNaturally(e.getLocation(), appleDrop);
/* 57 */       if (Herobrine.specialEffects.booleanValue() == true) {
/* 58 */         w.createExplosion(e.getLocation(), -1.0F);
/*    */       }
/* 66 */       event.setDroppedExp(0);
/* 67 */       event.getDrops().clear();
/*    */     }
/*    */   }
/*    */ }