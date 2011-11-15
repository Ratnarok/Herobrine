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
                if (Herobrine.modifyWorld==true) {
/* 41 */            createTorch(p);
                }
/* 50 */     } else if (eventChoice == 2) {
                if (Herobrine.modifyWorld==true) {
                    createSign(p);
                }
/*    */     } else if (eventChoice == 3) {
                 playSound(p);
             } else if (eventChoice == 4) {
                 randomSpawn(p);
             }
/*    */ }

        public void createTorch(Player p) {
            Block redstoneTorch = p.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
            Block groundBlock = redstoneTorch.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
            if ((redstoneTorch.getTypeId() == 0) && (groundBlock.getTypeId() != 0)) {
                redstoneTorch.setType(Material.REDSTONE_TORCH_ON);
                plugin.logEvent(2, p.getName());
            }
        }
        
        public void createSign(Player p) {
/* 52 */         Block signPost = p.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
/* 53 */         Block groundBlock = signPost.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
/* 54 */         if ((signPost.getTypeId() == 0) && (groundBlock.getTypeId() != 0)) {
/* 55 */           signPost.setType(Material.SIGN_POST);
/* 56 */           BlockState signState = signPost.getState();
/* 57 */           Sign signBlock = (Sign)signState;
/* 58 */           Random signOptions = new Random();
/* 59 */           int signText = signOptions.nextInt(9);
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
/*    */           } else if (signText==6) {
                     signBlock.setLine(1, ChatColor.DARK_RED + "I'm alive.");
                   } else if (signText==7) {
                     signBlock.setLine(1, ChatColor.DARK_RED + "R.I.P.");
                     signBlock.setLine(2, ChatColor.DARK_RED + p.getName());
                   } else if (signText==8) {
                     signBlock.setLine(1, ChatColor.DARK_RED + "You don't know");
                     signBlock.setLine(2, ChatColor.DARK_RED + "what you did.");
                   } else {
                     signBlock.setLine(1, ChatColor.DARK_RED + "I'm not");
                     signBlock.setLine(2, ChatColor.DARK_RED + "a myth.");
                   }
/* 74 */           plugin.logEvent(3, p.getName()); 
/*    */        }  
        }
        
        public void playSound(Player p) {
            p.getWorld().playEffect(p.getLocation(), Effect.CLICK2, 5);
            plugin.logEvent(4, p.getName());
        }
        
        public void randomSpawn(Player p) {
            if (plugin.isDead()) {
                World w = p.getWorld();
                w.createExplosion(p.getLocation().add(3, 0, 3), -1.0F);
                w.strikeLightning(p.getLocation().add(3, 0, 3));
                Herobrine.trackingEntity = true;
                w.spawnCreature(p.getLocation().add(3, 0, 3), CreatureType.ZOMBIE);
                p.sendMessage("<Herobrine> Remember me, " + p.getName() + "?");
                Block pBlock = p.getLocation().getBlock();
                pBlock.setType(Material.FIRE);
                plugin.logEvent(5, p.getName());
            }
        }
}