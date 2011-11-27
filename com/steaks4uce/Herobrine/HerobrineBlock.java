/*    */ package com.steaks4uce.Herobrine;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.CreatureType;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.block.BlockIgniteEvent;
/*    */ import org.bukkit.event.block.BlockListener;
         import java.util.Random;
/*    */ 
/*    */ public class HerobrineBlock extends BlockListener
/*    */ {
/*    */   public static Herobrine plugin;
/*    */   public static int i;
/*    */ 
/*    */   public HerobrineBlock(Herobrine instance) {
/* 18 */     plugin = instance;
/*    */   }
/*    */ 
/*    */   public void onBlockIgnite(BlockIgniteEvent event)
/*    */   {
/* 23 */     Block b = event.getBlock();
/* 24 */     if (event.getCause() == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
/* 25 */       Player p = event.getPlayer();
/* 26 */       World w = event.getBlock().getWorld();
/* 27 */       Block netherRack = b.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
/* 28 */       Block mossyCobble = b.getLocation().subtract(0.0D, 2.0D, 0.0D).getBlock();
/* 29 */       if ((netherRack.getType() == Material.NETHERRACK) && (mossyCobble.getType() == Material.MOSSY_COBBLESTONE) && (plugin.herobrineModel == null)) {
/* 30 */         if (Herobrine.changeEnvironment.booleanValue() == true) {
/* 31 */           w.setStorm(true);
/* 32 */           w.setTime(14200); 
/*    */         }
/* 36 */         if (Herobrine.removeMossyCobblestone.booleanValue() == true) {
/* 37 */           mossyCobble.setType(Material.COBBLESTONE);
/*    */         }
/* 39 */         if (Herobrine.specialEffects.booleanValue() == true) {
/* 40 */           w.strikeLightning(b.getLocation());
/* 41 */           w.createExplosion(b.getLocation(), -1.0F);
/*    */         }
/* 43 */         if (Herobrine.sendMessages.booleanValue() == true) {
                   Random messages = new Random();
                   int message = messages.nextInt(3);
                   if (message == 0) {
/* 44 */               plugin.getServer().broadcastMessage("<Herobrine> Only god can help you now!");
                   } else if (message == 1) {
                       plugin.getServer().broadcastMessage("<Herobrine> Prepare for hell!");
                   } else {
                       plugin.getServer().broadcastMessage("<Herobrine> Never doubt my power!");
                   }
/*    */         }
/* 46 */         Herobrine.trackingEntity = Boolean.valueOf(true);
/* 47 */         w.spawnCreature(b.getLocation(), CreatureType.ZOMBIE);
/* 48 */         plugin.logEvent(1, p.getName()); 
/*    */       }
/*    */     }
/*    */   }
/*    */ }