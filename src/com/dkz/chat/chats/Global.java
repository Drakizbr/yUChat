package com.dkz.chat.chats;

import com.dkz.chat.Main;
import com.dkz.chat.manager.ChatAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Global {

    public static ArrayList<Player> delay = new ArrayList<>();
    public static Main plugin = Main.getMain;
    public static void globalMessage(Player p, String label, String[] strings){
        if(plugin.getConfig().getBoolean("Utilizar-Delay")) {
            if (!delay.contains(p)) {
                delay.add(p);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        delay.clear();
                    }
                }, 20L);

                for (Player online : plugin.getServer().getOnlinePlayers()) {
                    if (p.hasPermission("yuchat.channel.destaque")) {
                        online.sendMessage("");
                        online.sendMessage(ChatAPI.getMessagaAdjust(p, getChannelPerCommand(label), ChatAPI.getMessage(strings)));
                        online.sendMessage("");
                    } else {
                        online.sendMessage(ChatAPI.getMessagaAdjust(p, getChannelPerCommand(label), ChatAPI.getMessage(strings)));
                    }

                    if (ChatAPI.getMessage(strings).contains(online.getDisplayName())) {
                        if (plugin.getConfig().getBoolean("Mencionar" + ".Ativado")) {
                            ChatAPI.mention(online.getDisplayName(), p.getDisplayName());
                        }
                    }
                }
            } else {
                p.sendMessage("§b[yUChat] §7Você deve aguardar para enviar outra mensagem.");
                return;
            }
        }else{
            for (Player online : plugin.getServer().getOnlinePlayers()) {
                if (p.hasPermission("yuchat.channel.destaque")) {
                    online.sendMessage("");
                    online.sendMessage(ChatAPI.getMessagaAdjust(p, getChannelPerCommand(label), ChatAPI.getMessage(strings)));
                    online.sendMessage("");
                } else {
                    online.sendMessage(ChatAPI.getMessagaAdjust(p, getChannelPerCommand(label), ChatAPI.getMessage(strings)));
                }

                if (ChatAPI.getMessage(strings).contains(online.getDisplayName())) {
                    if (plugin.getConfig().getBoolean("Mencionar" + ".Ativado")) {
                        ChatAPI.mention(online.getDisplayName(), p.getDisplayName());
                    }
                }
            }
        }
    }


    public static String getChannelPerCommand(String cmd) {
        for (String Canal : plugin.getConfig().getConfigurationSection("Canais").getKeys(false)) {
            if (plugin.getConfig().getString("Canais." + Canal + ".command").equals(cmd)) {
                return Canal;
            }
        }
        return "§b[yUChat] §cAlgo não está correto, entre em contato com o desenvolvedor.";
    }

}
