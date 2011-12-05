package com.steaks4uce.Herobrine.listeners;
import com.steaks4uce.Herobrine.Herobrine;
import com.steaks4uce.Herobrine.effects.SmokeArea;

import java.util.Random;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class HeroPlayer extends PlayerListener {
    public static Herobrine plugin;
    Random chanceGenerator = new Random();

    public HeroPlayer(Herobrine instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        int eventChoice = this.chanceGenerator.nextInt(Herobrine.innerChance + 1);
        if (eventChoice == 1) {
            if (Herobrine.modifyWorld == true) {
                createTorch(p);
            }
        } else if (eventChoice == 2) {
            if (Herobrine.modifyWorld == true) {
                createSign(p);
            }
        } else if (eventChoice == 3) {
            playSound(p);
        } else if (eventChoice == 4) {
            if (Herobrine.modifyWorld == true) {
                randomFire(p);
            }
        } else if (eventChoice == 5) {
            attackPlayer(p);
        } else if (eventChoice == 6) {
            appearNear(p);
        } else if (eventChoice == 7) {
            dropItem(p);
        } else if (eventChoice == 8) {
            placeChest(p);
        }
    }

    public void createTorch(Player p) {
        Block torch = p.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
        Block groundBlock = torch.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        if ((torch.getTypeId() == 0) && (groundBlock.getTypeId() != 0)) {
            torch.setType(Material.REDSTONE_TORCH_ON);
            plugin.logEvent(2, p.getName());
            plugin.smokes.add(new SmokeArea(torch.getLocation()));
        }
    }
        
    public void createSign(Player p) {
        Block signPost = p.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
        Block groundBlock = signPost.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        if ((signPost.getTypeId() == 0) && (groundBlock.getTypeId() != 0)) {
            signPost.setType(Material.SIGN_POST);
            BlockState signState = signPost.getState();
            Sign signBlock = (Sign)signState;
            Random signOptions = new Random();
            int signText = signOptions.nextInt(9);
            if (signText == 1) {
                signBlock.setLine(1, "I'm watching.");
            } else if (signText == 2) {
                signBlock.setLine(1, "Stop.");
            } else if (signText == 3) {
                signBlock.setLine(1, "You'll join");
                signBlock.setLine(2, "me soon.");
            } else if (signText == 4) {
                signBlock.setLine(1, "You can't");
                signBlock.setLine(2, "escape.");
            } else if (signText==5) {
                signBlock.setLine(1, "Remember me?");
            } else if (signText==6) {
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
            plugin.logEvent(3, p.getName()); 
            plugin.smokes.add(new SmokeArea(signPost.getLocation()));
        }  
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
            if (b.getType() == Material.AIR) {
                Herobrine.trackingEntity = true;
                w.spawnCreature(p.getLocation().add(5, 0, 0), CreatureType.ZOMBIE);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        plugin.herobrineModel.remove();
                    }
                }, 60L);
                plugin.logEvent(6, p.getName());
            }
        }
    }
        
    public void randomFire(Player p) {
        Block fire = p.getLocation().add(5, 0, 0).getBlock();
        Block ground = fire.getLocation().subtract(0, 1, 0).getBlock();
        if (fire.getTypeId() == 0 && ground.getTypeId() != 0 && Herobrine.useFire == true) {
            fire.setType(Material.FIRE);
            plugin.logEvent(7, p.getName());
        }
    }
        
    public void dropItem(Player p) {
        World w = p.getWorld();
        Random random = new Random();
        int rand = random.nextInt(3);
        if (rand == 1) {
            w.dropItemNaturally(p.getLocation().add(5, 0, 0), new ItemStack(Material.BUCKET, 1));
        } else {
            w.dropItemNaturally(p.getLocation().add(5, 0, 0), new ItemStack(Material.FLINT_AND_STEEL, 1));
        }
        plugin.logEvent(9, p.getName());
    }
    
    public void placeChest(Player p) {
        Block b = p.getLocation().add(5, 0, 0).getBlock();
        b.setType(Material.LOCKED_CHEST);
        plugin.smokes.add(new SmokeArea(b.getLocation()));
    }
}