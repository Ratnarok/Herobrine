package com.steaks4uce.Herobrine;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;

public class HerobrineSpout extends SpoutListener {
    public static Herobrine plugin;
    
    public HerobrineSpout(Herobrine instance) {
        plugin = instance;
    }
    
    public void onSpoutCraftEnable(SpoutCraftEnableEvent event) {
        SpoutManager.getFileManager().addToCache(plugin, "http://www.nkrecklow.com/herobrine.png");
    }
}
