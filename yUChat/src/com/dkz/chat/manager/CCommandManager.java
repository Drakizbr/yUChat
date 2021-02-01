package com.dkz.chat.manager;

import com.dkz.chat.Main;
import com.dkz.chat.chats.Global;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;



public class CCommandManager extends BukkitCommand {

    public static Main plugin = Main.getMain;

    protected CCommandManager(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] strings) {

        if (commandSender instanceof Player) {
            for (String Canal : plugin.getConfig().getConfigurationSection("Canais").getKeys(false)) {

                // Checar comando
                if (label.equalsIgnoreCase(plugin.getConfig().getString("Canais." + Canal + ".command"))) {
                    // Checar Permissão
                    if (commandSender.hasPermission("yuchat.channel." + Canal)) {
                        if (strings.length > 0) {

                            int dist;
                            if (plugin.getConfig().get("Canais." + getChannelPerCommand(label) + ".Distancia") != null) {
                                dist = plugin.getConfig().getInt("Canais." + getChannelPerCommand(label) + ".Distancia");
                            } else {
                                dist = 999;
                            }

                            switch (dist) {
                                case 999:
                                    Global.globalMessage((Player) commandSender, label, strings);
                                    break;
                                case -1:
                                    for (Player online : plugin.getServer().getOnlinePlayers()) {
                                        if (online.hasPermission(getChannelPermission(getChannelPerCommand(label)))) {
                                            online.sendMessage(ChatAPI.getMessagaAdjust((Player) commandSender, getChannelPerCommand(label), ChatAPI.getMessage(strings)));
                                        }
                                    }
                                    break;
                                default:
                                    ChatAPI.sendDistanceMessage((Player) commandSender, getChannelPerCommand(label), ChatAPI.getMessage(strings), getChannelDistance(getChannelPerCommand(label)));
                            }

                        } else {
                            commandSender.sendMessage("§b[yUChat] " + Main.FALHA_CHAT);
                            return false;
                        }
                    } else {
                        commandSender.sendMessage("§b[yUChat] " + Main.SEM_PERMISSAO_CHAT);
                        return false;

                    }
                }
            }
        } else {
            commandSender.sendMessage("§b[yUChat] §co console nao pode utilizar isso.");
            return false;
        }


        return false;
    }

    public String getChannelPermission(String channel) {
        return "yuchat.channel." + channel;
    }

    public static int getChannelDistance(String channel) {
        return plugin.getConfig().getInt("Canais." + channel + ".Distancia");
    }


    public String getChannelPerCommand(String cmd) {
        for (String Canal : plugin.getConfig().getConfigurationSection("Canais").getKeys(false)) {
            if (plugin.getConfig().getString("Canais." + Canal + ".command").equals(cmd)) {
                return Canal;
            }
        }
        return "§b[yUChat] §cAlgo não está correto, entre em contato com o desenvolvedor.";
    }


}
