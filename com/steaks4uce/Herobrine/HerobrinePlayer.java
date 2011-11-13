/*    */ package com.steaks4uce.Herobrine;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.ChatColor;
         import org.bukkit.Effect;
/*    */ import org.bukkit.Material;
import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.block.Sign;
import org.bukkit.entity.CreatureType;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.player.PlayerListener;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */ 
/*    */ public class HerobrinePlayer extends PlayerListener
/*    */ {
/*    */   public static Herobrine plugin;
/*    */   public static int i;
/* 21 */   Random chanceGenerator = new Random();
/*    */ 
/*    */   public HerobrinePlayer(Herobrine instance) {
/* 24 */     plugin = instance;
/*    */   }
/*    */ 
/*    */   public void onPlayerMove(PlayerMoveEvent event) {
/* 29 */     Player p = event.getPlayer();
/* 31 */     int eventChoice = this.chanceGenerator.nextInt(Herobrine.innerChance + 1);
/* 32 */     if (eventChoice == 1) {
/* 40 */       if ((!p.hasPermission("herobrine.exempt")) && (Herobrine.modifyWorld.booleanValue() == true)) {
/* 41 */         Block redstoneTorch = p.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
/* 42 */         Block groundBlock = redstoneTorch.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
/* 43 */         if ((redstoneTorch.getTypeId() == 0) && (groundBlock.getTypeId() != 0)) {
/* 44 */           redstoneTorch.setType(Material.REDSTONE_TORCH_ON);
/* 45 */           Herobrine.affectedPlayer = p.getName();
/* 46 */           Herobrine.occuringEvent = 2;
/* 47 */           plugin.eventLogger();
/*    */         }
/*    */       }
/* 50 */     } else if (eventChoice == 2) {
/* 51 */       if ((!p.hasPermission("herobrine.exempt")) && (Herobrine.modifyWorld.booleanValue() == true)) {
/* 52 */         Block signPost = p.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
/* 53 */         Block groundBlock = signPost.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
/* 54 */         if ((signPost.getTypeId() == 0) && (groundBlock.getTypeId() != 0)) {
/* 55 */           signPost.setType(Material.SIGN_POST);
/* 56 */           BlockState signState = signPost.getState();
/* 57 */           Sign signBlock = (Sign)signState;
/* 58 */           Random signOptions = new Random();
/* 59 */           int signText = signOptions.nextInt(6);
/* 60 */           if (signText == 1) {
/* 61 */             signBlock.setLine(1, ChatColor.DARK_RED + "I'm watching.");
/* 62 */           } else if (signText == 2) {
/* 63 */             signBlock.setLine(1, ChatColor.DARK_RED + "Stop.");
/* 64 */           } else if (signText == 3) {
/* 65 */             signBlock.setLine(1, ChatColor.DARK_RED + "You'll join");
/* 66 */             signBlock.setLine(2, ChatColor.DARK_RED + "me soon.");
/* 67 */           } else if (signText == 4) {
/* 68 */             signBlock.setLine(1, ChatColor.DARK_RED + "You can't");
/* 69 */             signBlock.setLine(2, ChatColor.DARK_RED + "escape.");
/*    */           } else if (signText==5) {
/* 71 */             signBlock.setLine(1, ChatColor.DARK_RED + "Remember me?");
/*    */           } else {
                     signBlock.setLine(1, ChatColor.DARK_RED + "I'm alive.");
                   }
/* 74 */           Herobrine.affectedPlayer = p.getName();
/* 75 */           Herobrine.occuringEvent = 3;
/* 76 */           plugin.eventLogger();
/*    */         }
/*    */       }
/*    */     } else if (eventChoice == 3) {
               if ((!p.hasPermission("herobrine.exempt"))) {
                   p.getWorld().playEffect(p.getLocation(), Effect.CLICK2, 5);
                   Herobrine.affectedPlayer = p.getName();
                   Herobrine.occuringEvent = 4;
                   plugin.eventLogger();
               }
             } else if (eventChoice == 4) {
                 if ((!p.hasPermission("herobrine.exempt")) && plugin.isDead()) {
                     World w = p.getWorld();
                     w.createExplosion(p.getLocation().add(3, 0, 3), -1.0F);
                     w.strikeLightning(p.getLocation().add(3, 0, 3));
                     Herobrine.trackingEntity = true;
                     w.spawnCreature(p.getLocation().add(3, 0, 3), CreatureType.ZOMBIE);
                     p.sendMessage("<Herobrine> Remember me, " + p.getName() + "?");
                     Block pBlock = p.getLocation().getBlock();
                     Block hBlock = p.getLocation().add(3, 0, 3).getBlock();
                     pBlock.setType(Material.FIRE);
                     hBlock.setType(Material.REDSTONE_TORCH_ON);
                     Herobrine.affectedPlayer = p.getName();
                     Herobrine.occuringEvent = 5;
                     plugin.eventLogger();
                 }
             }
/*    */ }
}