package com.gmail.favorlock.bcpb.listeners;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitTask;

import com.gmail.favorlock.bcpb.BungeeChatPlusBukkit;
import com.gmail.favorlock.bcpb.tasks.PluginMessageTask;

public class VaultListener implements Listener {
	
	BungeeChatPlusBukkit plugin;
	
	public VaultListener(BungeeChatPlusBukkit plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void login(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String group = plugin.getChat().getPrimaryGroup(player);
		String prefix = "";
		String suffix = "";
		
		if (plugin.getChat().getPlayerPrefix(player) != null) {
			prefix = plugin.getChat().getPlayerPrefix(player);
		} else if (plugin.getChat().getGroupPrefix(player.getWorld(), group) != null) {
			prefix = plugin.getChat().getGroupPrefix(player.getWorld(), group);
		}
		if (plugin.getChat().getPlayerSuffix(player) != null) {
			suffix = plugin.getChat().getPlayerSuffix(player);
		} else if (plugin.getChat().getGroupSuffix(player.getWorld(), group) != null) {
			suffix = plugin.getChat().getGroupSuffix(player.getWorld(), group);
		}
		
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		DataOutputStream output = new DataOutputStream(bStream);
		
		try {
			output.writeUTF("VaultAffix");
			output.writeUTF(player.getName());
			output.writeUTF(prefix);
			output.writeUTF(suffix);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BukkitTask task = new PluginMessageTask(this.plugin, player, bStream).runTaskLater(this.plugin, 1);
	}

}
