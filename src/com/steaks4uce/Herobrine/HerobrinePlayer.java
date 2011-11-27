/*    */ package com.steaks4uce.Herobrine;
/*    */ 
/*    */ import java.util.Random;
         import org.bukkit.Effect;
         import org.bukkit.Location;
/*    */ import org.bukkit.Material;
         import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.block.Sign;
         import org.bukkit.entity.CreatureType;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.player.PlayerListener;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
         import org.bukkit.inventory.ItemStack;
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
                 if (Herobrine.modifyWorld==true) {
                    randomFire(p);
                 }
             }  else if (eventChoice == 5) {
                 appearNear(p);
             } else if (eventChoice == 6) {
                 attackPlayer(p);
             } else if (eventChoice == 7) {
                 dropItem(p);
             }
/*    */ }

        public void createTorch(Player p) {
            Block torch = p.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
            Block groundBlock = torch.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
            if ((torch.getTypeId() == 0) && (groundBlock.getTypeId() != 0)) {
                torch.setType(Material.REDSTONE_TORCH_ON);
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
/* 61 */             signBlock.setLine(1, "I'm watching.");
/* 62 */           } else if (signText == 2) {
/* 63 */             signBlock.setLine(1, "Stop.");
/* 64 */           } else if (signText == 3) {
/* 65 */             signBlock.setLine(1, "You'll join");
/* 66 */             signBlock.setLine(2, "me soon.");
/* 67 */           } else if (signText == 4) {
/* 68 */             signBlock.setLine(1, "You can't");
/* 69 */             signBlock.setLine(2, "escape.");
/*    */           } else if (signText==5) {
/* 71 */             signBlock.setLine(1, "Remember me?");
/*    */           } else if (signText==6) {
                     signBlock.setLine(1, "I'm alive.");
                   } else if (signText==7) {
                     signBlock.setLine(1, "I told you, ");
                     signBlock.setLine(2, p.getName() + ".");
                   } else if (signText==8) {
                     signBlock.setLine(1, "You don't know");
                     signBlock.setLine(2, "what you did.");
                   } else {
                     signBlock.setLine(1, "I'm not");
                     signBlock.setLine(2, "a myth.");
                   }
/* 74 */           plugin.logEvent(3, p.getName()); 
/*    */        }  
        }
        
        public void playSound(Player p) {
            p.getWorld().playEffect(p.getLocation(), Effect.CLICK2, 5);
            plugin.logEvent(4, p.getName());
        }
        
        public void attackPlayer(Player p) {
            if (plugin.isDead() == true) {
                World w = p.getWorld();
                w.createExplosion(p.getLocation().add(3, 0, 3), -1.0F);
                Herobrine.trackingEntity = true;
                w.spawnCreature(p.getLocation().add(3, 0, 3), CreatureType.ZOMBIE);
                Random messages = new Random();
                int message = messages.nextInt(3);
                if (message == 1) {
                    p.sendMessage("<Herobrine> Remember me, " + p.getName() + "?");
                } else if (message == 2) {
                    p.sendMessage("<Herobrine> Never doubt me, " + p.getName() + "!");
                } else {
                    p.sendMessage("<Herobrine> I will not die!");
                }
                plugin.logEvent(5, p.getName());
            }
        }
        
        public void appearNear(Player p) {
            if (plugin.isDead() == true) {
                World w = p.getWorld();
                Block b = p.getLocation().add(5, 0, 0).getBlock();
                if (b.getType()!=Material.AIR) {
                    Herobrine.trackingEntity = true;
                    w.spawnCreature(p.getLocation().add(5, 0, 0), CreatureType.ZOMBIE);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            plugin.herobrineModel.remove();
                        }
                    }, 40L);
                    plugin.logEvent(6, p.getName());
                }
            }
        }
        
        public void randomFire(Player p) {
            Block fire = p.getLocation().add(5, 0, 0).getBlock();
            Block ground = fire.getLocation().subtract(0, 1, 0).getBlock();
            if (fire.getTypeId()==0 && ground.getTypeId()!=0) {
                fire.setType(Material.FIRE);
                plugin.logEvent(7, p.getName());
            }
        }

        public void dropStack(Material m, int amount, World w, Location loc) {
            ItemStack created = new ItemStack(m, amount);
            w.dropItemNaturally(loc, created);
        }
        
        public void dropItem(Player p) {
            World w = p.getWorld();
            Location loc = p.getLocation().add(5, 0, 0);
            Random random = new Random();
            int rand = random.nextInt(3);
            if (rand == 0) {
                dropStack(Material.COBBLESTONE, 1, w, loc);
            } else if (rand == 1) {
                dropStack(Material.TORCH, 1, w, loc);
            } else if (rand == 2) {
                dropStack(Material.WOOD_PICKAXE, 1, w, loc);
            } else if (rand == 3) {
                dropStack(Material.BREAD, 1, w, loc);
            } else {
                dropStack(Material.WOOD, 1, w, loc);
            }
            plugin.logEvent(9, p.getName());
        }
}