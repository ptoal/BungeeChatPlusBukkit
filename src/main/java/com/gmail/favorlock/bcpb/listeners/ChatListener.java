package com.gmail.favorlock.bcpb.listeners;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatListener implements Listener {
	
	JavaPlugin plugin;
	
	public ChatListener(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		if (!event.isCancelled()) {
			Player player = event.getPlayer();
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			DataOutputStream output = new DataOutputStream(bStream);
		
			String message = event.getMessage();
		
			try {
				output.writeUTF("FactionChat");
				output.writeUTF(player.getName());
				output.writeUTF(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			event.getPlayer().sendPluginMessage(this.plugin, "BungeeChatPlus", bStream.toByteArray());
		}
	}

}
