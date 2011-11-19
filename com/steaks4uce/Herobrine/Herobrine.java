/*     */ package com.steaks4uce.Herobrine;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class Herobrine extends JavaPlugin
/*     */ {
/*  19 */   private final HerobrineEntity entityListener = new HerobrineEntity(this);
/*  20 */   private final HerobrineBlock blockListener = new HerobrineBlock(this);
/*  21 */   private final HerobrinePlayer playerListener = new HerobrinePlayer(this);
/*  22 */   static final Logger log = Logger.getLogger("Minecraft");

            //Important internal variables
/*  26 */   public static Boolean trackingEntity = Boolean.valueOf(false);
/*     */   protected Entity herobrineModel;
            
            //Default values in case of loading failure
/*  25 */   public static int innerChance = 100000;
/*  28 */   public static Boolean removeMossyCobblestone = Boolean.valueOf(false);
/*  29 */   public static Boolean changeEnvironment = Boolean.valueOf(true);
/*  30 */   public static Boolean specialEffects = Boolean.valueOf(true);
/*  31 */   public static Boolean sendMessages = Boolean.valueOf(true);
/*  32 */   public static Boolean modifyWorld = Boolean.valueOf(true);

            //Configuration file variables
/*  33 */   public static String mainDirectory = "plugins/Herobrine";
/*  34 */   public static File configFile = new File(mainDirectory + File.separator + "config.properties");
/*  35 */   public static Properties settingsFile = new Properties();
/*     */ 
/*     */   public void onDisable() {}
/*     */ 
/*     */   public void onEnable() {
/*  42 */     new File(mainDirectory).mkdir();
/*  43 */     if (!configFile.exists())
/*     */       try {
/*  45 */         configFile.createNewFile();
/*  46 */         FileOutputStream out = new FileOutputStream(configFile);
/*  47 */         settingsFile.put("modify-world", Boolean.toString(modifyWorld.booleanValue()));
/*  48 */         settingsFile.put("send-messages", Boolean.toString(sendMessages.booleanValue()));
/*  49 */         settingsFile.put("special-effects", Boolean.toString(specialEffects.booleanValue()));
/*  50 */         settingsFile.put("change-environment", Boolean.toString(changeEnvironment.booleanValue()));
/*  51 */         settingsFile.put("remove-mossystone", Boolean.toString(removeMossyCobblestone.booleanValue()));
/*  52 */         settingsFile.put("action-chance", Integer.toString(innerChance));
/*  53 */         settingsFile.store(out, "Configuration file for Herobrine 0.7");
/*     */       } catch (IOException ex) {
/*  55 */         Logger.getLogger(Herobrine.class.getName()).log(Level.SEVERE, null, ex);
/*     */       }
/*     */     else {
/*     */       try {
/*  59 */         FileInputStream in = new FileInputStream(configFile);
/*     */         try {
/*  61 */           settingsFile.load(in);
/*  62 */           modifyWorld = Boolean.valueOf(settingsFile.getProperty("modify-world"));
/*  63 */           sendMessages = Boolean.valueOf(settingsFile.getProperty("send-messages"));
/*  64 */           changeEnvironment = Boolean.valueOf(settingsFile.getProperty("change-environment"));
/*  65 */           removeMossyCobblestone = Boolean.valueOf(settingsFile.getProperty("remove-mossystone"));
/*  66 */           specialEffects = Boolean.valueOf(settingsFile.getProperty("special-effects"));
/*  67 */           innerChance = Integer.parseInt(settingsFile.getProperty("action-chance"));
/*     */         } catch (IOException ex) {
/*  69 */           Logger.getLogger(Herobrine.class.getName()).log(Level.SEVERE, null, ex);
/*     */         }
/*     */       } catch (FileNotFoundException ex) {
/*  72 */         Logger.getLogger(Herobrine.class.getName()).log(Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*  75 */     PluginDescriptionFile pdfFile = getDescription();
/*  76 */     log.info("[Herobrine] Herobrine " + pdfFile.getVersion() + " is enabled!");
/*  77 */     PluginManager pm = getServer().getPluginManager();
/*  78 */     pm.registerEvent(Event.Type.BLOCK_IGNITE, this.blockListener, Event.Priority.Normal, this);
/*  79 */     pm.registerEvent(Event.Type.ENTITY_DAMAGE, this.entityListener, Event.Priority.Normal, this);
/*  80 */     pm.registerEvent(Event.Type.ENTITY_DEATH, this.entityListener, Event.Priority.Normal, this);
/*  81 */     pm.registerEvent(Event.Type.CREATURE_SPAWN, this.entityListener, Event.Priority.Normal, this);
/*  82 */     pm.registerEvent(Event.Type.PLAYER_MOVE, this.playerListener, Event.Priority.Normal, this);
              pm.registerEvent(Event.Type.BLOCK_PLACE, this.blockListener, Event.Priority.Normal, this);
/*     */   }

            public void logEvent(int event, String p) {
                if (event == 1) {
/*  87 */           log.info("[Herobrine] Herobrine was summoned by " + p + "!");
/*  88 */       } else if (event == 2) {
/*  91 */           log.info("[Herobrine] Herobrine placed a torch near " + p + "!");
/*  92 */       } else if (event == 3) {
/*  93 */           log.info("[Herobrine] Herobrine placed a sign near " + p + "!");
                } else if (event == 4) {
                    log.info("[Herobrine] Herobrine played a sound for " + p + "!"); 
/*  94 */       } else if (event == 5) {
                    log.info("[Herobrine] Herobrine is attacking " + p + "!");
                } else if (event == 6) {
                    log.info("[Herobrine] Herobrine appeared near " + p + "!");
                }
            }   

            public boolean isDead() {
               if (herobrineModel == null || herobrineModel.isDead() == true) { return true;
               } else { return false;}
            }
/*     */ }