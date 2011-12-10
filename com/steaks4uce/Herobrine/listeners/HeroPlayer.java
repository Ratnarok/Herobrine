package com.steaks4uce.Herobrine.listeners;
import com.steaks4uce.Herobrine.Herobrine;
import com.steaks4uce.Herobrine.events.Events;

import java.util.Random;
import org.bukkit.entity.Player;
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
        }
    }
}