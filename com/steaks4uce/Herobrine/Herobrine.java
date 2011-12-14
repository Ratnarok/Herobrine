package com.steaks4uce.Herobrine;
import com.steaks4uce.Herobrine.listeners.HeroPlayer;
import com.steaks4uce.Herobrine.listeners.HeroBlock;
import com.steaks4uce.Herobrine.listeners.HeroEntity;
import com.steaks4uce.Herobrine.effects.SmokeArea;
import com.steaks4uce.Herobrine.events.Events;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Herobrine extends JavaPlugin {
    private final HeroEntity entityListener = new HeroEntity(this);
    private final HeroBlock blockListener = new HeroBlock(this);
    private final HeroPlayer playerListener = new HeroPlayer(this);
    public static final Logger log = Logger.getLogger("Minecraft");
    public static Boolean trackingEntity = Boolean.valueOf(false);
    public Entity hbEntity;
    public static int innerChance = 100000;
    public static Boolean removeMossyCobblestone = Boolean.valueOf(true);
    public static Boolean changeEnvironment = Boolean.valueOf(true);
    public static Boolean specialEffects = Boolean.valueOf(true);
    public static Boolean useFire = Boolean.valueOf(true);
    public static Boolean fireTrails = Boolean.valueOf(true);
    public static Boolean sendMessages = Boolean.valueOf(true);
    public static Boolean isAttacking = false;
    public static Boolean canAttack = true;
    public static Boolean modifyWorld = Boolean.valueOf(true);
    public static String mainDirectory = "plugins/Herobrine";
    public static File configFile = new File(mainDirectory + File.separator + "Settings.properties");
    public static Properties settingsFile = new Properties();
    public ArrayList<SmokeArea> smokes = new ArrayList<SmokeArea>();
    Events actions = new Events(this);

    @Override
    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        log.info("[Herobrine] Herobrine " + pdfFile.getVersion() + " is disabled!");
    }

    @Override
    public void onEnable() {
        new File(mainDirectory).mkdir();
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                FileOutputStream out = new FileOutputStream(configFile);
                settingsFile.put("modify-world", Boolean.toString(modifyWorld.booleanValue()));
                settingsFile.put("send-messages", Boolean.toString(sendMessages.booleanValue()));
                settingsFile.put("special-effects", Boolean.toString(specialEffects.booleanValue()));
                settingsFile.put("change-environment", Boolean.toString(changeEnvironment.booleanValue()));
                settingsFile.put("remove-mossystone", Boolean.toString(removeMossyCobblestone.booleanValue()));
                settingsFile.put("action-chance", Integer.toString(innerChance));
                settingsFile.put("allow-fire", Boolean.toString(useFire));
                settingsFile.put("fire-trails", Boolean.toString(fireTrails));
                settingsFile.put("can-attack", Boolean.toString(canAttack));
                settingsFile.store(out, "Configuration file for Herobrine 1.1");
            } catch (IOException ex) {
                log.info("[Herobrine] Failed to create the configuration file!");
            }
        } else {
            try {
                FileInputStream in = new FileInputStream(configFile);
                try {
                    settingsFile.load(in);
                    modifyWorld = Boolean.valueOf(settingsFile.getProperty("modify-world"));
                    sendMessages = Boolean.valueOf(settingsFile.getProperty("send-messages"));
                    changeEnvironment = Boolean.valueOf(settingsFile.getProperty("change-environment"));
                    removeMossyCobblestone = Boolean.valueOf(settingsFile.getProperty("remove-mossystone"));
                    specialEffects = Boolean.valueOf(settingsFile.getProperty("special-effects"));
                    innerChance = Integer.parseInt(settingsFile.getProperty("action-chance"));
                    useFire = Boolean.valueOf(settingsFile.getProperty("allow-fire"));
                    fireTrails = Boolean.valueOf(settingsFile.getProperty("fire-trails"));
                    canAttack = Boolean.valueOf(settingsFile.getProperty("can-attack"));
                } catch (IOException ex) {
                    log.info("[Herobrine] Failed to load the configuration file!");
                }
            } catch (FileNotFoundException ex) {
                log.info("[Herobrine] Failed to load the configuration file!");
            }
        }
        
        PluginDescriptionFile pdfFile = getDescription();
        log.info("[Herobrine] Herobrine " + pdfFile.getVersion() + " is enabled!");
        
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.BLOCK_IGNITE, this.blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, this.entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, this.entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.CREATURE_SPAWN, this.entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_TARGET, this.entityListener, Event.Priority.Normal, this); 
        
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
                if (isDead() == false) {
                    hbEntity.setVelocity(hbEntity.getLocation().getDirection().multiply(0.8));
                }
                for (SmokeArea smoke : smokes) {
                    World w = smoke.loc.getWorld();
                    Location l = smoke.loc;
                    w.playEffect(l, Effect.SMOKE, 0);
                }
                if (isDead() == false && fireTrails == true && isAttacking == true) {
                    Block b = hbEntity.getLocation().getBlock();
                    Block g = b.getLocation().subtract(0, 1, 0).getBlock();
                    if (b.getType().equals(Material.AIR) && !(g.getType().equals(Material.AIR))) {
                        b.setType(Material.FIRE);
                    }
                }
                if (isDead() == false) {
                    Random rand = new Random();
                    int doJump = rand.nextInt(4);
                    if (doJump == 1) {
                        actions.superJump(0.50);
                    }
                }
            }
        }, 0L, 20L);
        
        Plugin spout = pm.getPlugin("Spout");
        if (spout != null) {
            this.log.info("[Herobrine] Successfully hooked into Spout!");
        } else {
            this.log.info("[Herobrine] Failed to find Spout!");
            pm.disablePlugin(this);
        }
    }

    public boolean isDead() {
        if (hbEntity == null || hbEntity.isDead() == true) { 
            return true;
        } else { 
            return false;
        }
    }
    
    public boolean canSpawn(World w) {
        if (w.getAllowMonsters() == true) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equals("hb")) {
            try {
                if (sender instanceof Player) {
                    if (args[0].equalsIgnoreCase("appear")) {
                        Player p = (Player)sender;
                        Player target = getServer().getPlayer(args[1]);
                        if (p.isOp() == true) {
                            if (canSpawn(target.getWorld())) {
                                actions.appearNear(target);
                                p.sendMessage(ChatColor.GREEN + "Herobrine appeared near " + target.getName() + "!");
                            } else {
                                p.sendMessage(ChatColor.RED + "Herobrine is not allowed to spawn in " + target.getName() + "'s world!");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "You do not have permission for this!");
                        }
                    } else if (args[0].equalsIgnoreCase("bury")) {
                        Player p = (Player)sender;
                        Player target = getServer().getPlayer(args[1]);
                        if (p.isOp() == true) {
                            if (target.isOnline()) {
                                actions.buryPlayer(target);
                                p.sendMessage(ChatColor.GREEN + "Herobrine has buried " + target.getName() + "!");
                            } else {
                                p.sendMessage(ChatColor.RED + "Player not found!");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("reset")) {
                        Player p = (Player)sender;
                        if (p.isOp() == true) {
                            hbEntity.remove();
                            p.sendMessage(ChatColor.GREEN + "Herobrine has been removed!");
                        } else {
                            p.sendMessage(ChatColor.RED + "You do not have permission for this!");
                        }
                    } else if (args[0].equalsIgnoreCase("attack")) {
                        Player p = (Player)sender;
                        Player target = getServer().getPlayer(args[1]);
                        if (p.isOp() == true) {
                            if (canSpawn(target.getWorld())) {
                                actions.appearNear(target);
                                p.sendMessage(ChatColor.GREEN + "Herobrine is now attacking " + target.getName() + "!");
                            } else {
                                p.sendMessage(ChatColor.RED + "Herobrine is not allowed to spawn in " + target.getName() + "'s world!");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "You do not have permission for this!");
                        }
                    } else if (args[0].equalsIgnoreCase("help")) {
                        Player p = (Player)sender;
                        p.sendMessage(ChatColor.GREEN + "Herobrine (1.1) Commands Guide:");
                        p.sendMessage("attack - Attack a certain player.");
                        p.sendMessage("appear - Appear near a certain player.");
                        p.sendMessage("bury - Bury a certain player alive.");
                        p.sendMessage("reset - Remove him in case of error.");
                    } else if (args[0].equalsIgnoreCase("info")) {
                        Player p = (Player)sender;
                        if (p.getName().equals("steaks4uce")) {
                            p.sendMessage("Inner chance: " + Integer.toString(innerChance));
                            p.sendMessage("Modify world: " + modifyWorld);
                            p.sendMessage("Change environment: " + changeEnvironment);
                            p.sendMessage("Special effects: " + specialEffects);
                            p.sendMessage("Version: " + getDescription().getVersion());
                        }
                    } else {
                        Player p = (Player)sender;
                        p.sendMessage(ChatColor.RED + "Not a known command...");
                        p.sendMessage(ChatColor.RED + "Type '/hb help' for help");
                    }
                } else { 
                    log.info("[Herobrine] You must be a player to use this command!");
                }
            } catch (Exception ex) {
                if (sender instanceof Player) {
                    Player p = (Player)sender;
                    p.sendMessage(ChatColor.RED + "Failed to use command! Is everything correct?");
                    p.sendMessage(ChatColor.RED + "Type '/hb help' for help");
                } else {
                    log.info("[Herobrine] You must be a player to use this command!");
                }
            }
        }
        return true;
    }
}