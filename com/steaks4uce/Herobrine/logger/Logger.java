package com.steaks4uce.Herobrine.logger;

import com.steaks4uce.Herobrine.Herobrine;

public class Logger {
    
    public void event(int event, String p) {
        if (event == 1) {
            Herobrine.log.info("[Herobrine] Herobrine was summoned by " + p + "!");
        } else if (event == 2) {
            Herobrine.log.info("[Herobrine] Herobrine placed a torch near " + p + "!");
        } else if (event == 3) {
            Herobrine.log.info("[Herobrine] Herobrine placed a sign near " + p + "!");
        } else if (event == 4) {
            Herobrine.log.info("[Herobrine] Herobrine played a sound for " + p + "!"); 
        } else if (event == 5) {
            Herobrine.log.info("[Herobrine] Herobrine is attacking " + p + "!");
        } else if (event == 6) {
            Herobrine.log.info("[Herobrine] Herobrine appeared near " + p + "!");
        } else if (event == 7) {
            Herobrine.log.info("[Herobrine] Herobrine placed fire near " + p + "!");
        } else if (event == 8) {
            Herobrine.log.info("[Herobrine] Herobrine was defeated by " + p + "!");
        } else if (event == 9) {
            Herobrine.log.info("[Herobrine] Herobrine dropped an item near " + p + "!");
        } else if (event == 10) {
            Herobrine.log.info("[Herobrine] Herobrine placed a chest near " + p + "!");
        } else if (event == 11) {
            Herobrine.log.info("[Herobrine] Herobrine exploded a chest for " + p + "!");
        } else if (event == 12) {
            Herobrine.log.info("[Herobrine] Herobrine buried " + p + "!");
        } else if (event == 13) {
            Herobrine.log.info("[Herobrine] Herobrine converted sheep to spiders near " + p + "!");
        }
    } 
}
