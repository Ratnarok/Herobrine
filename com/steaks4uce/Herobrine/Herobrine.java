package com.steaks4uce.Herobrine;
import com.steaks4uce.Herobrine.effects.FireTrail;
import com.steaks4uce.Herobrine.listeners.HeroPlayer;
import com.steaks4uce.Herobrine.listeners.HeroBlock;
import com.steaks4uce.Herobrine.listeners.HeroEntity;
import com.steaks4uce.Herobrine.effects.SmokeArea;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
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
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Herobrine extends JavaPlugin {
    private final HeroEntity entityListener = new HeroEntity(this);
    private final HeroBlock blockListener = new HeroBlock(this);
    private final HeroPlayer playerListener = new HeroPlayer(this);
    static final Logger log = Logger.getLogger("Minecraft");
    public static Boolean trackingEntity = Boolean.valueOf(false);
    public Entity herobrineModel;
    public static int innerChance = 100000;
    public static Boolean removeMossyCobblestone = Boolean.valueOf(false);
    public static Boolean changeEnvironment = Boolean.valueOf(true);
    public static Boolean specialEffects = Boolean.valueOf(true);
    public static Boolean useFire = Boolean.valueOf(true);
    public static Boolean fireTrails = Boolean.valueOf(true);
    public static Boolean sendMessages = Boolean.valueOf(true);
    public static Boolean modifyWorld = Boolean.valueOf(true);
    public static String mainDirectory = "plugins/Herobrine";
    public static File configFile = new File(mainDirectory + File.separator + "Settings.properties");
    public static Properties settingsFile = new Properties();
    public ArrayList<SmokeArea> smokes = new ArrayList<SmokeArea>();

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
                settingsFile.store(out, "Configuration file for Herobrine 1.0");
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
                    herobrineModel.setVelocity(herobrineModel.getLocation().getDirection().multiply(0.8));
                }
                for (SmokeArea smoke : smokes) {
                    World w = smoke.loc.getWorld();
                    Location l = smoke.loc;
                    w.playEffect(l, Effect.SMOKE, 0);
                }
                if (isDead() == false && fireTrails == true) {
                    Block b = herobrineModel.getLocation().getBlock();
                    b.setType(Material.FIRE);
                }
            }
        }, 0L, 20L);
    }

    public boolean isDead() {
        if (herobrineModel == null || herobrineModel.isDead() == true) { 
            return true;
        } else { 
            return false;
        }
    }

    public void logEvent(int event, String p) {
        if (event == 1) {
            log.info("[Herobrine] Herobrine was summoned by " + p + "!");
        } else if (event == 2) {
            log.info("[Herobrine] Herobrine placed a torch near " + p + "!");
        } else if (event == 3) {
            log.info("[Herobrine] Herobrine placed a sign near " + p + "!");
        } else if (event == 4) {
            log.info("[Herobrine] Herobrine played a sound for " + p + "!"); 
        } else if (event == 5) {
            log.info("[Herobrine] Herobrine is attacking " + p + "!");
        } else if (event == 6) {
            log.info("[Herobrine] Herobrine appeared near " + p + "!");
        } else if (event == 7) {
            log.info("[Herobrine] Herobrine placed fire near " + p + "!");
        } else if (event == 8) {
            log.info("[Herobrine] Herobrine was defeated by " + p + "!");
        } else if (event == 9) {
            log.info("[Herobrine] Herobrine dropped an item near " + p + "!");
        }
    }  

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equals("hb")) {
            if (sender instanceof Player) {
                if (args[0].equalsIgnoreCase("appear")) {
                    Player p = (Player)sender;
                    Player target = getServer().getPlayer(args[1]);
                    if (p.isOp() == true) {
                        if (target.isOnline()) {
                            HeroPlayer events = new HeroPlayer(this);
                            events.appearNear(target);
                        } else {
                            p.sendMessage(ChatColor.RED + "Player not found!");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "You do not have permission for this!");
                    }
                } else if (args[0].equalsIgnoreCase("reset")) {
                    Player p = (Player)sender;
                    if (p.isOp() == true) {
                        herobrineModel.remove();
                    } else {
                        p.sendMessage(ChatColor.RED + "You do not have permission for this!");
                    }
                } else if (args[0].equalsIgnoreCase("attack")) {
                    Player p = (Player)sender;
                    Player target = getServer().getPlayer(args[1]);
                    if (p.isOp() == true) {
                        if (target.isOnline()) {
                            HeroPlayer events = new HeroPlayer(this);
                            events.attackPlayer(target);
                        } else {
                            p.sendMessage(ChatColor.RED + "Player not found!");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "You do not have permission for this!");
                    }
                } else if (args[0].equalsIgnoreCase("help")) {
                    Player p = (Player)sender;
                    p.sendMessage(ChatColor.RED + "Herobrine (1.0) Commands:");
                    p.sendMessage("attack - Attack a certain player.");
                    p.sendMessage("appear - Appear near a certain player.");
                    p.sendMessage("reset - Remove the entity and reset.");
                } else {
                    Player p = (Player)sender;
                    p.sendMessage(ChatColor.RED + "Invalid argument!");
                    p.sendMessage(ChatColor.RED + "Type /hb help for help");
                }
            } else { 
                log.info("[Herobrine] You must be a player to use this command!");
            }
        }
        return true;
    }
}