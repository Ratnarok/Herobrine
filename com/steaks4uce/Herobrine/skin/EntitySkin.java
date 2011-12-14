package com.steaks4uce.Herobrine.skin;

import com.avaje.ebean.config.ServerConfig;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Recipe;
import org.bukkit.map.MapView;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.EntitySkinType;

public class EntitySkin implements Server{

    public void setEntitySkin(LivingEntity target, String url, EntitySkinType type) {
        SpoutManager.getPlayerManager().getGlobalInfo().setEntitySkin(target, url, type);
    }

    public String getEntitySkin(LivingEntity target, EntitySkinType type) {
        return SpoutManager.getPlayerManager().getGlobalInfo().getEntitySkin(target, type);
    }

    public void resetEntitySkin(LivingEntity target) {
        SpoutManager.getPlayerManager().getGlobalInfo().setEntitySkin(target, null);
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getVersion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getBukkitVersion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Player[] getOnlinePlayers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getMaxPlayers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getPort() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getViewDistance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getIp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getServerName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getServerId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean getAllowEnd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean getAllowNether() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasWhitelist() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setWhitelist(boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<OfflinePlayer> getWhitelistedPlayers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reloadWhitelist() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int broadcastMessage(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getUpdateFolder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public File getUpdateFolderFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Player getPlayer(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Player getPlayerExact(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Player> matchPlayer(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PluginManager getPluginManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BukkitScheduler getScheduler() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ServicesManager getServicesManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<World> getWorlds() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public World createWorld(String string, Environment e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public World createWorld(String string, Environment e, long l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public World createWorld(String string, Environment e, ChunkGenerator cg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public World createWorld(String string, Environment e, long l, ChunkGenerator cg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public World createWorld(WorldCreator wc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean unloadWorld(String string, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean unloadWorld(World world, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public World getWorld(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public World getWorld(UUID uuid) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MapView getMap(short s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MapView createMap(World world) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reload() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Logger getLogger() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PluginCommand getPluginCommand(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void savePlayers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean dispatchCommand(CommandSender cs, String string) throws CommandException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void configureDbConfig(ServerConfig sc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addRecipe(Recipe recipe) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String[]> getCommandAliases() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getSpawnRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSpawnRadius(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean getOnlineMode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean getAllowFlight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int broadcast(String string, String string1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<String> getIPBans() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void banIP(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void unbanIP(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<OfflinePlayer> getBannedPlayers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<OfflinePlayer> getOperators() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GameMode getDefaultGameMode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDefaultGameMode(GameMode gm) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ConsoleCommandSender getConsoleSender() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public File getWorldContainer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public OfflinePlayer[] getOfflinePlayers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}