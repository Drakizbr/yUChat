package com.dkz.chat.listeners;

import com.dkz.chat.Main;
import com.dkz.chat.manager.CCommandManager;
import com.dkz.chat.manager.ChatAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Events implements Listener {
    private static Main plugin = Main.getMain;



    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerSendMessage(AsyncPlayerChatEvent event){
        Player p = event.getPlayer();
            if(p.hasPermission("yuchat.channel.cor")) {
                ChatAPI.sendDistanceMessage(p, (String) plugin.getConfig().get("Canal-Padrao"), event.getMessage().replaceAll("&", "§"), CCommandManager.getChannelDistance((String) plugin.getConfig().get("Canal-Padrao")));
            }else{
                ChatAPI.sendDistanceMessage(p, (String) plugin.getConfig().get("Canal-Padrao"), event.getMessage(), CCommandManager.getChannelDistance((String) plugin.getConfig().get("Canal-Padrao")));
            }
        event.setCancelled(true);
    }

}
