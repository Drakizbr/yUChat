package com.dkz.chat.lib;

import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionEX {



    public static PermissionUser getUser(Player p){
        try {
            return PermissionsEx.getUser(p);
        }catch (NoClassDefFoundError e){
            return null;
        }
    }

}
