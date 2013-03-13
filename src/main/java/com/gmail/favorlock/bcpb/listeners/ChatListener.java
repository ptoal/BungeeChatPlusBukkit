package com.gmail.favorlock.bcpb.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.gmail.favorlock.bcpb.BungeeChatPlusBukkit;

public class ChatListener implements Listener {
	
	BungeeChatPlusBukkit plugin;
	
	public ChatListener(BungeeChatPlusBukkit plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (!event.isCancelled()) {	
			if (plugin.getBCPConfig().Settings_EnableRegex) {
				plugin.getRegexManager().filterChat(event);
			}
		}
	}

}
