package de.cubenation.plugins.utils.chatapi.Chatter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.cubenation.plugins.utils.chatapi.ResourceConverter;

public class BroadcastResourceAsynchron {
    public static void chat(Plugin plugin, ResourceConverter converter, String resourceString, Object... parameter) {
        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            ChatResourceAsynchron.chat(plugin, converter, onlinePlayer, resourceString, parameter);
        }
    }
}
