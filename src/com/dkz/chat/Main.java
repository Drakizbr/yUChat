package com.dkz.chat;


import com.dkz.chat.lib.PlaceholderAPI;
import com.dkz.chat.listeners.Events;
import com.dkz.chat.manager.ChatAPI;
import com.dkz.chat.manager.ChatManager;
import com.dkz.chat.manager.Commands;
import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;
import sun.security.provider.ConfigFile;


public class Main extends JavaPlugin {

    public static Main getMain;
    public static String canalPadrao;
    public static int distancia;


    //Mensagens
    public static String SEM_PERMISSAO;
    public static String SEM_PERMISSAO_CHAT;
    public static String FALHA_CHAT;
    public static String SOZINHO;
    public static int timeDelay;


    @Override
    public void onEnable() {

        getMain = this;
        saveDefaultConfig();




        // Valores a definir
        distancia = getConfig().getInt("Canais" + ".Local" + ".Distancia");
        canalPadrao = getConfig().getString("Canal-Padrão");
        timeDelay = getConfig().getInt("Canais" + ".Global" + ".Delay");


        registrarComandos();

        // Registrar Eventos
        getServer().getPluginManager().registerEvents(new ChatAPI(), this);
        getServer().getPluginManager().registerEvents(new Events(), this);
        getServer().getConsoleSender().sendMessage("§2===========================================");
        getServer().getConsoleSender().sendMessage(" ");
        getServer().getConsoleSender().sendMessage("§b[yUChat] §aPlugin ativado com sucesso.");
        getServer().getConsoleSender().sendMessage("§b[yUChat] §ePlugin feito por: " + getDescription().getAuthors());
        getServer().getConsoleSender().sendMessage("§b[yUChat] §eVersao: " + getDescription().getVersion());
        getServer().getConsoleSender().sendMessage("§b[yUChat] §3(" + ChatAPI.getChannelsSize() + ") Canais registrados.");
        getServer().getConsoleSender().sendMessage(" ");
        getServer().getConsoleSender().sendMessage("§2===========================================");

        PlaceholderAPI.hasPlaceholder = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");

        SEM_PERMISSAO = getConfig().getString("Lang" + ".SEM_PERMISSAO").replaceAll("&", "§");
        SEM_PERMISSAO_CHAT = getConfig().getString("Lang" + ".SEM_PERMISSAO_CHAT").replaceAll("&", "§");
        FALHA_CHAT = getConfig().getString("Lang" + ".FALHA_CHAT").replaceAll("&", "§");
        SOZINHO = getConfig().getString("Lang" + ".SOZINHO").replaceAll("&", "§");

    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§4===========================================");
        getServer().getConsoleSender().sendMessage(" ");
        getServer().getConsoleSender().sendMessage("§b[yUChat] §cPlugin desativado com sucesso.");
        getServer().getConsoleSender().sendMessage("§b[yUChat] §ePlugin feito por: "+getDescription().getAuthors());
        getServer().getConsoleSender().sendMessage("§b[yUChat] §eVersao: "+getDescription().getVersion());
        getServer().getConsoleSender().sendMessage(" ");
        getServer().getConsoleSender().sendMessage("§4===========================================");
    }

    private void registrarComandos(){


        ChatManager.registrarComandos();

        getCommand("yuchat").setExecutor(new Commands());
        getCommand("espiar").setExecutor(new Commands());
        getCommand("r").setExecutor(new Commands());
        getCommand("tell").setExecutor(new Commands());
        getCommand("clearchat").setExecutor(new Commands());

    }


}
