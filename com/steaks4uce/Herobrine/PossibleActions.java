package com.steaks4uce.Herobrine;
import com.steaks4uce.Herobrine.text.CustomLogger;
import com.steaks4uce.Herobrine.text.TextGenerator;
import com.steaks4uce.Herobrine.tunnels.TunnelHandler;

import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class PossibleActions {
    public static Herobrine plugin;
    CustomLogger log = new CustomLogger();
    
    public PossibleActions(Herobrine instance) {
        plugin = instance;
    }
    
    public void createTorch(Player p) {
        Block torch = p.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
        Block groundBlock = torch.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        if (torch.getTypeId() == 0 && groundBlock.getTypeId() != 0) {
            torch.setType(Material.REDSTONE_TORCH_ON);
            log.event(2, p.getName());
            plugin.addSmoke(torch.getLocation());
        }
    }
        
    public void createSign(Player p) {
        Block signPost = p.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
        Block groundBlock = signPost.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        if (signPost.getTypeId() == 0 && groundBlock.getTypeId() != 0) {
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
            plugin.addSmoke(signPost.getLocation());
        }  
    }
        
    public void playSound(Player p) {
        p.getWorld().playEffect(p.getLocation(), Effect.CLICK2, 5);
        log.event(4, p.getName());
    }
        
    public void attackPlayer(Player p) {
        if (plugin.isDead() && Herobrine.canAttack && plugin.canSpawn(p.getWorld())) {
            World w = p.getWorld();
            w.createExplosion(p.getLocation().add(3, 0, 3), -1.0F);
            Herobrine.trackingEntity = true;
            w.spawnCreature(p.getLocation().add(3, 0, 3), CreatureType.ZOMBIE);
            Zombie z = (Zombie) plugin.hbEntity;
            z.setTarget(p);
            Herobrine.isAttacking = true;
            if (Herobrine.sendMessages) {
                TextGenerator tg = new TextGenerator();
                p.sendMessage(tg.getMessage());
            }
            if (p.getGameMode().equals(GameMode.CREATIVE)) {
                p.setGameMode(GameMode.SURVIVAL);
            }
            log.event(5, p.getName());
        }
    }
        
    public void appearNear(Player p) {
        if (plugin.isDead() == true && plugin.canSpawn(p.getWorld())) {
            World w = p.getWorld();
            Block b = p.getLocation().add(5, 0, 0).getBlock();
            if (b.getType().equals(Material.AIR)) {
                Herobrine.trackingEntity = true;
                w.spawnCreature(p.getLocation().add(5, 0, 0), CreatureType.ZOMBIE);
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
        if (fire.getTypeId() == 0 && ground.getTypeId() != 0 && Herobrine.useFire) {
            fire.setType(Material.FIRE);
            log.event(7, p.getName());
        }
    }
    
    public void placeChest(Player p) {
        Block chest = p.getLocation().add(5, 0, 0).getBlock();
        Block ground = chest.getLocation().subtract(0, 1, 0).getBlock();
        if (ground.getTypeId() != 0 && chest.getTypeId() == 0) {
            plugin.addSmoke(chest.getLocation());
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
    
    public void sendMessage(Player p) {
        String s = "<" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "> ";
        p.sendMessage(s + "I'm here.");
        log.event(13, p.getName());
    }
    
    public void digTunnel(Player p) {
        Random r = new Random();
        TunnelHandler th = new TunnelHandler();
        Location l = p.getLocation().subtract(0, 5, 0);
        int s = r.nextInt(26);
        if (s < 10) {
            s = 10;
        }
        th.createTunnel(s, l, 4);
        log.event(15, p.getName());
    }
    
    public void createCrystal(Player p) {
        Location l = p.getLocation().add(5, 0, 0);
        Block b = l.getBlock();
        Block a = b.getLocation().subtract(0, 1, 0).getBlock();
        World w = p.getWorld();
        if (b.getType().equals(Material.AIR) && !(a.getType().equals(Material.AIR))) {
            w.spawn(l, EnderCrystal.class);
            log.event(16, p.getName());
        }
    }
    
    public void createTNT(Player p) {
        Location l = p.getLocation().add(5, 0, 0);
        Block a = l.subtract(0, 1, 0).getBlock();
        Block b = l.getBlock();
        World w = p.getWorld();
        if (a.getType() != Material.AIR && b.getType().equals(Material.AIR)) {
            w.spawn(l, TNTPrimed.class);
            log.event(17, p.getName());
        }
    }
}