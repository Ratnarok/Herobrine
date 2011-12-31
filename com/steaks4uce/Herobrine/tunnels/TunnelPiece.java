package com.steaks4uce.Herobrine.tunnels;

import org.bukkit.block.Block;

public class TunnelPiece {
    Block block;
    int type;
    
    public TunnelPiece(Block b, int t) {
        block = b;
        type = t;
    }
}
