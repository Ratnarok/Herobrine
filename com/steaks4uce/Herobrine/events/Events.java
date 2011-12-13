package com.steaks4uce.Herobrine.events;
import com.steaks4uce.Herobrine.Herobrine;
import com.steaks4uce.Herobrine.effects.SmokeArea;
import com.steaks4uce.Herobrine.logger.Logger;

import java.util.Random;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Events {
    public static Herobrine plugin;
    Logger log = new Logger();
    
    public Events(Herobrine instance) {
        plugin = instance;
    }
    
    public void createTorch(Player p) {
        Block torch = p.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
        Block groundBlock = torch.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        if ((torch.getTypeId() == 0) && (groundBlock.getTypeId() != 0)) {
            torch.setType(Material.REDSTONE_TORCH_ON);
            log.event(2, p.getName());
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
            log.event(3, p.getName()); 
            plugin.smokes.add(new SmokeArea(signPost.getLocation()));
        }  
    }
        
    public void playSound(Player p) {
        p.getWorld().playEffect(p.getLocation(), Effect.CLICK2, 5);
        log.event(4, p.getName());
    }
        
    public void attackPlayer(Player p) {
        if (plugin.isDead() == true && Herobrine.canAttack == true && plugin.canSpawn(p.getWorld())) {
            World w = p.getWorld();
            w.createExplosion(p.getLocation().add(3, 0, 3), -1.0F);
            Herobrine.trackingEntity = true;
            w.spawnCreature(p.getLocation().add(3, 0, 3), CreatureType.PIG_ZOMBIE);
            Herobrine.isAttacking = true;
            Random messages = new Random();
            int message = messages.nextInt(3);
            if (message == 1) {
                p.sendMessage("<Herobrine> Remember me, " + p.getName() + "?");
            } else if (message == 2) {
                p.sendMessage("<Herobrine> Never doubt me, " + p.getName() + "!");
            } else {
                p.sendMessage("<Herobrine> I will not die!");
            }
            log.event(5, p.getName());
        }
    }
        
    public void appearNear(Player p) {
        if (plugin.isDead() == true && plugin.canSpawn(p.getWorld())) {
            World w = p.getWorld();
            Block b = p.getLocation().add(5, 0, 0).getBlock();
            if (b.getType() == Material.AIR) {
                Herobrine.trackingEntity = true;
                w.spawnCreature(p.getLocation().add(5, 0, 0), CreatureType.PIG_ZOMBIE);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        plugin.hbEntity.remove();
                    }
                }, 60L);
                log.event(6, p.getName());
            }
        }
    }
        
    public void randomFire(Player p) {
        Block fire = p.getLocation().add(5, 0, 0).getBlock();
        Block ground = fire.getLocation().subtract(0, 1, 0).getBlock();
        if (fire.getTypeId() == 0 && ground.getTypeId() != 0 && Herobrine.useFire == true) {
            fire.setType(Material.FIRE);
            log.event(7, p.getName());
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
        log.event(9, p.getName());
    }
    
    public void placeChest(Player p) {
        Block chest = p.getLocation().add(5, 0, 0).getBlock();
        Block ground = chest.getLocation().subtract(0, 1, 0).getBlock();
        if (ground.getTypeId() != 0 && chest.getTypeId() == 0) {
            plugin.smokes.add(new SmokeArea(chest.getLocation()));
            chest.setType(Material.LOCKED_CHEST);
            log.event(10, p.getName());
        }
    }
    
    public void explodeChest(Player p, Location chest) {
        Block b = chest.getBlock();
        World w = b.getWorld();
        w.createExplosion(b.getLocation(), -1.0F);
        w.dropItemNaturally(b.getLocation(), new ItemStack(Material.FLINT_AND_STEEL, 1));
        log.event(11, p.getName());
    }
    
    public void buryPlayer(Player p) {
        final Block b1 = p.getLocation().subtract(0, 1, 0).getBlock();
        Block b2 = b1.getLocation().subtract(0, 1, 0).getBlock();
        Block b3 = b2.getLocation().subtract(0, 1, 0).getBlock();
        if (b1.getTypeId() != 0 && b2.getTypeId() != 0 && b3.getTypeId() != 0) {
            final Material type = b1.getType();
            b1.setType(Material.AIR);
            b2.setType(Material.AIR);
            b3.setType(Material.AIR);
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    b1.setType(type);
                }
            }, 20L);
            log.event(12, p.getName());
        }
    }
    
    public void superJump(Double i) {
        Vector velocity = plugin.hbEntity.getVelocity();
        Vector newVelocity = new Vector(velocity.getX(), i, velocity.getZ());
        plugin.hbEntity.setVelocity(newVelocity);
    }
}
