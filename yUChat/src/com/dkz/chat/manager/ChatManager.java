package com.dkz.chat.manager;

import com.dkz.chat.Main;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;


import java.util.logging.Logger;


public class ChatManager {

    public static Main plugin = Main.getMain;


    public static void registrarComandos() {
        int err = 0;
        for(String Canal : plugin.getConfig().getConfigurationSection("Canais").getKeys(false)) {
                try {
                    new CCommandManager(plugin.getConfig().getString("Canais." + Canal + ".command"));
                    ((CraftServer) plugin.getServer()).getCommandMap().register(plugin.getConfig().getString("Canais." + Canal + ".command"), new CCommandManager(plugin.getConfig().getString("Canais." + Canal + ".command")));

                }catch (NullPointerException ex){
                  err++;
                    if(err > 0) {
                        Logger log = plugin.getLogger();
                        log.warning(err + " canal(is) sem comando encontrado(s).");
                    }
                }
        }
    }


}
