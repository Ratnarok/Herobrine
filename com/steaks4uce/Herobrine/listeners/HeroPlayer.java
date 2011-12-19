package com.steaks4uce.Herobrine.listeners;
import com.steaks4uce.Herobrine.Herobrine;
import com.steaks4uce.Herobrine.events.Events;

import java.util.Random;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

public class HeroPlayer extends PlayerListener {
    public static Herobrine plugin;
    Random chanceGenerator = new Random();
    Events actions = new Events(plugin);

    public HeroPlayer(Herobrine instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        int eventChoice = this.chanceGenerator.nextInt(Herobrine.innerChance + 1);
        if (eventChoice == 1) {
            if (Herobrine.modifyWorld == true) {
                actions.createTorch(p);
            }
        } else if (eventChoice == 2) {
            if (Herobrine.modifyWorld == true) {
                actions.createSign(p);
            }
        } else if (eventChoice == 3) {
            actions.playSound(p);
        } else if (eventChoice == 4) {
            if (Herobrine.modifyWorld == true) {
                actions.randomFire(p);
            }
        } else if (eventChoice == 5) {
            actions.attackPlayer(p);
        } else if (eventChoice == 6) {
            actions.appearNear(p);
        } else if (eventChoice == 7) {
            actions.dropItem(p);
        } else if (eventChoice == 8) {
            if (Herobrine.modifyWorld == true) {
                actions.buryPlayer(p);
            }
        } else if (eventChoice == 9) {
            if (Herobrine.modifyWorld == true) {
                actions.placeChest(p);
            }
        }
    }
    
    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        String m = event.getMessage();
        m = m.toLowerCase();
        if (m.contains("herobrine")) {
            Random rand = new Random();
            int i = rand.nextInt(26);
            if (i == 1) {
                Player p = event.getPlayer();
                actions.appearNear(p);
            }
        }
    }
}