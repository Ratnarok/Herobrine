package com.steaks4uce.Herobrine.text;

import com.steaks4uce.Herobrine.formats.Message;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.ChatColor;

public class TextGenerator {
    ArrayList<Message> m = new ArrayList<Message>();
    
    void addMessage(String s, int i) {
        String n = "<" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "> ";
        m.add(new Message(n + s, i));
    }
    
    public String getMessage() {
        populateMessages();
        Random r = new Random();
        int t = r.nextInt(10);
        String g = null;
        for (Message a : m) {
            if (a.id == t) {
                g = a.msg;
            }
        }
        return g;
    }
    
    void populateMessages() {
        addMessage("Only god can help you now!", 0);
        addMessage("Never doubt my power!", 1);
        addMessage("I will always survive!", 2);
        addMessage("I am your god!", 3);
        addMessage("Prepare for true hell!", 4);
        addMessage("I will prevail!", 5);
        addMessage("This isn't the end...", 6);
        addMessage("I will be back...", 7);
        addMessage("I'll be watching you...", 8);
        addMessage("I'll always be here.", 9);
        addMessage("You have yet to know...", 10);
        addMessage("Be prepared...", 11);
    }
}
