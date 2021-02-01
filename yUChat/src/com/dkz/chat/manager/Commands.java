package com.dkz.chat.manager;

import com.dkz.chat.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commands implements CommandExecutor {


    private static Main plugin = Main.getMain;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player p = (Player) sender;
            if (label.equalsIgnoreCase("yuchat")) {
                if (sender.hasPermission("yuchat.admin")) {
                    if (args.length > 0) {
                        plugin.getServer().getPluginManager().disablePlugin(plugin);
                        plugin.getServer().getPluginManager().enablePlugin(plugin);
                        plugin.reloadConfig();
                        sender.sendMessage("§b[yUChat] §aPlugin recarregado com sucesso.");
                    } else {
                        sender.sendMessage("§b[yUChat] §eArgumentos inválidos.");
                        return false;
                    }
                } else {
                    sender.sendMessage("§b[yUChat] " + Main.SEM_PERMISSAO);
                    return false;
                }
            } else if (label.equalsIgnoreCase("tell")) {
                if (args.length > 1) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        sb.append(args[i]).append(" ");
                    }
                    String msg = sb.toString().trim();
                    ChatAPI.sendTellMessage(p, args[0], msg);
                } else {
                    sender.sendMessage("§b[yUChat] §cUtilize /tell (nick) (mensagem)");
                    return false;
                }

            } else if (label.equalsIgnoreCase("espiar")) {
                if (p.hasPermission("yuchat.espiar")) {
                    if (ChatAPI.espiando.get(p) != null) {
                        ChatAPI.espiando.put(p, !ChatAPI.espiando.get(p));
                        if (ChatAPI.espiando.get(p)) {
                            p.sendMessage("§b[yUChat] §eO modo espião foi §aATIVADO");
                        } else {
                            p.sendMessage("§b[yUChat] §eO modo espião foi §cDESATIVADO");
                        }
                    } else {
                        ChatAPI.espiando.put(p, true);
                        p.sendMessage("§b[yUChat] §eO modo espião foi §aATIVADO");

                    }
                } else {
                    sender.sendMessage("§b[yUChat] " + Main.SEM_PERMISSAO);
                    return false;
                }
            } else if (label.equalsIgnoreCase("r")) {
                if (ChatAPI.responder.get(p) != null) {
                    Player paraResponder = ChatAPI.responder.get(p);
                    if (args.length > 0) {
                        ChatAPI.sendTellMessage(p, paraResponder.getDisplayName(), ChatAPI.getMessage(args));
                    } else {
                        sender.sendMessage("§b[yUChat] §cUtilize /r (mensagem)");
                        return false;
                    }
                } else {
                    p.sendMessage("§b[yUChat] §eVocê não tem ninguém para responder.");
                    return false;
                }
            }else if(label.equalsIgnoreCase("clearchat")){
                if(!sender.hasPermission("yuchat.clearchat")) {
                    sender.sendMessage("§b[yUChat] " + Main.SEM_PERMISSAO);
                    return false;
                }else{
                    for(Player pp : plugin.getServer().getOnlinePlayers()) {

                        for (int i = 0; i < 500; i++) {
                            pp.sendMessage("");
                        }
                        pp.sendMessage("§b[yUChat] §aO Chat foi limpo por "+((Player) sender).getDisplayName());
                    }

                }

            }

        } else {
            sender.sendMessage("§b[yUChat] " + Main.SEM_PERMISSAO);
            return false;
        }
        return false;

    }
}
