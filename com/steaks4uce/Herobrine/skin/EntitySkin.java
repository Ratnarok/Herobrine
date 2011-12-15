package com.steaks4uce.Herobrine.skin;

import org.bukkit.entity.LivingEntity;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.EntitySkinType;

public class EntitySkin {

    public void setSkin(LivingEntity target, String url) {
        SpoutManager.getPlayerManager().getGlobalInfo().setEntitySkin(target, url, EntitySkinType.DEFAULT);
    }
}