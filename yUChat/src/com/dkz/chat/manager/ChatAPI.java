package com.dkz.chat.manager;


import com.dkz.chat.Main;
import com.dkz.chat.lib.PermissionEX;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatAPI implements Listener {

    private static Main plugin = Main.getMain;

    public static HashMap<Player, Boolean> espiando = new HashMap<Player, Boolean>();
    public static HashMap<Player, Player> responder = new HashMap<Player, Player>();


    // Obter a quantidade de canais registrados na config.yml
    public static int getChannelsSize() {
        return plugin.getConfig().getConfigurationSection("Canais").getKeys(false).size();
    }


    // Enviar mensagens para o chat local - padrão
    public static void sendDistanceMessage(Player enviou, String channel, String msg, int distance) {

        // Verificação de jogadores próximos ao jogador
        ArrayList<Player> proximos = new ArrayList<>();
        for (Player onPlayer : plugin.getServer().getOnlinePlayers()) {
            if (onPlayer.getLocation().getWorld() == enviou.getWorld()) {
                if (onPlayer.getLocation().distance(enviou.getLocation()) <= distance) {
                    if (onPlayer != enviou) {
                        proximos.add(onPlayer);
                    }
                }
            }
        }
        if (proximos.size() <= 0) {
            enviou.sendMessage(Main.SOZINHO);
            enviou.sendMessage(getMessagaAdjust(enviou, channel, msg));
            for (Player online : plugin.getServer().getOnlinePlayers()) {
                if (espiando.get(online) != null) {
                    if (espiando.get(online)) {
                        if (enviou != online) {
                            online.sendMessage(getMessagaAdjust(enviou, "Spy", PlaceholderAPI.setPlaceholders(enviou, msg)));
                        }
                    }
                }
            }

        } else {
            for (Player pProximo : proximos) {
                pProximo.sendMessage(getMessagaAdjust(enviou, channel, msg));
            }
            for (Player online : plugin.getServer().getOnlinePlayers()) {
                if (espiando.get(online) != null) {
                    if (espiando.get(online)) {
                        if (enviou != online) {
                            online.sendMessage(getMessagaAdjust(enviou, "Spy", PlaceholderAPI.setPlaceholders(enviou, msg)));
                        }
                    }
                }
            }
            enviou.sendMessage(getMessagaAdjust(enviou, channel, msg));
        }
    }



    // Transforma array string em uma string completa
    public static String getMessage(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String msg = sb.toString().trim();

        return msg;
    }


    public static void sendTellMessage(Player p1, String nick, String msg) {
        if (plugin.getServer().getPlayer(nick) != null) {
            if (!p1.getDisplayName().equals(nick)) {
                plugin.getServer().getPlayer(nick).sendMessage("§b[yUChat] §8O Jogador '" + p1.getDisplayName() + "' está te dizendo: §7" + msg.replaceAll("&", "§"));
                p1.sendMessage("§b[yUChat] §8Você sussurrou: §7'" + msg.replaceAll("&", "§") + "' §8para '" + nick + "'.");
                responder.put(p1, plugin.getServer().getPlayer(nick));
                responder.put(plugin.getServer().getPlayer(nick), p1);

                for (Player online : plugin.getServer().getOnlinePlayers()) {
                    if (espiando.get(online) != null) {
                        if (espiando.get(online)) {
                            online.sendMessage("§8[Spy] '§l" + p1.getDisplayName() + "§8' sussurou para '§l" + nick + "§8':§7 " + msg);
                        }
                    }
                }
            } else {
                p1.sendMessage("§b[yUChat] §cVocê não pode sussurar para sí mesmo.");
                return;
            }
        } else {
            p1.sendMessage("§b[yUChat] §cO jogador '" + nick + "' não está online.");
            return;
        }
    }


    public static void mention(String mentioned, String mention) {
        if (mentioned != mention) {
            Player p1 = plugin.getServer().getPlayer(mentioned);
            if (p1 != null) {
                p1.sendMessage("§e◆ §l" + mention + " §e◆ mencionou você em uma mensagem.");
                if (plugin.getConfig().getBoolean("Mencionar" + ".Som")) {
                    if (plugin.getConfig().getString("Mencionar" + ".Name") != null) {
                        try {
                            p1.playSound(p1.getLocation(), Sound.valueOf(plugin.getConfig().getString("Mencionar" + ".Name")), 1, 1);
                        } catch (IllegalArgumentException ex) {
                            plugin.getServer().getConsoleSender().sendMessage("§b[yUChat] §cUm som invalido foi definido na config.");
                            return;
                        }
                    }
                }
            }
        }
    }


    public static String getMessagaAdjust(Player p, String channel, String msg) {

        String $ = msg.replace("$", "?").replace("\\", "?").replaceAll("&", "§");
        if (PermissionEX.getUser(p) != null) {
            if (com.dkz.chat.lib.PlaceholderAPI.hasPlaceholder) {
                if(p.hasPermission("yuchat.channel.cor")) {
                    return PlaceholderAPI.setPlaceholders(p, plugin.getConfig().getString("Canais" + "." + channel + ".chat-prefix").replaceAll("&", "§").replaceAll("\\{prefix}", PermissionEX.getUser(p).getPrefix().replaceAll("&", "§")).replaceAll("\\{nick}", p.getDisplayName()).replaceAll("\\{msg}", $));
                }else{
                    return PlaceholderAPI.setPlaceholders(p, plugin.getConfig().getString("Canais" + "." + channel + ".chat-prefix").replaceAll("&", "§").replaceAll("\\{prefix}", PermissionEX.getUser(p).getPrefix().replaceAll("&", "§")).replaceAll("\\{nick}", p.getDisplayName()).replaceAll("\\{msg}", $));
                }
            } else {
                if(p.hasPermission("yuchat.channel.cor")) {
                    return plugin.getConfig().getString("Canais" + "." + channel + ".chat-prefix").replaceAll("&", "§").replaceAll("\\{prefix}", PermissionEX.getUser(p).getPrefix().replaceAll("&", "§")).replaceAll("\\{nick}", p.getDisplayName()).replaceAll("\\{msg}", $);
                }else{
                    return plugin.getConfig().getString("Canais" + "." + channel + ".chat-prefix").replaceAll("&", "§").replaceAll("\\{prefix}", PermissionEX.getUser(p).getPrefix().replaceAll("&", "§")).replaceAll("\\{nick}", p.getDisplayName()).replaceAll("\\{msg}", msg.replace("\\", "").replaceAll("$", ""));
                }
            }
        } else {
            if (com.dkz.chat.lib.PlaceholderAPI.hasPlaceholder) {
                return PlaceholderAPI.setPlaceholders(p, plugin.getConfig().getString("Canais" + "." + channel + ".chat-prefix").replaceAll("&", "§").replaceAll("\\{prefix}", "").replaceAll("\\{nick}", p.getDisplayName()).replaceAll("\\{msg}", $));
            } else {
                return plugin.getConfig().getString("Canais" + "." + channel + ".chat-prefix").replaceAll("&", "§").replaceAll("\\{prefix}", "").replaceAll("\\{nick}", p.getDisplayName()).replaceAll("\\{msg}", $);
            }
        }


    }


}
