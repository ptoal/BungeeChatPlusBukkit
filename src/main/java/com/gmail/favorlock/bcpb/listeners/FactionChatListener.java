package com.gmail.favorlock.bcpb.listeners;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import com.gmail.favorlock.bcpb.BungeeChatPlusBukkit;

public class FactionChatListener implements Listener {
	
	BungeeChatPlusBukkit plugin;
	
	public FactionChatListener(BungeeChatPlusBukkit plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
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
			
			if (plugin.getBCPConfig().Settings_EnableRegex) {
				plugin.getRegexManager().filterChat(event);
			}
		
			event.getPlayer().sendPluginMessage(this.plugin, "BungeeChatPlus", bStream.toByteArray());
		}
	}

}
