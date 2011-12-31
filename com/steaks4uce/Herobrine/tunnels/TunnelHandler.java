package com.steaks4uce.Herobrine.tunnels;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class TunnelHandler {
    ArrayList<TunnelPiece> tunnel = new ArrayList<TunnelPiece>();
    
    void addBlock(Block b, int t) {
        tunnel.add(new TunnelPiece(b, t));
    }
    
    void selectBlocks(int d, Location l) {
        int s = 0;
        while (s <= (d - 1)) {
            Block a = l.add(1, 0, 0).getBlock();
            Block b = a.getLocation().add(0, 1, 0).getBlock();
            Block c = a.getLocation().subtract(0, 1, 0).getBlock();
            addBlock(a, 0);
            addBlock(b, 0);
            addBlock(c, 1);
            s++;
        }
    }
    
    void setBlocks(int i) {
        for (TunnelPiece p : tunnel) {
            Block b = p.block;
            b.setType(Material.AIR);
            if (p.type == 1) {
                b.setTypeId(i);
            }
        }
    }
    
    public void createTunnel(int d, Location l, int i) {
        selectBlocks(d, l);
        if (canCreate()) {
            setBlocks(i);
        }
    }
    
    boolean canCreate() {
        boolean c = true;
        for (TunnelPiece p : tunnel) {
            Block b = p.block;
            if (b.getType().equals(Material.AIR)) {
                c = false;
            }
        }
        return c;
    }
}
