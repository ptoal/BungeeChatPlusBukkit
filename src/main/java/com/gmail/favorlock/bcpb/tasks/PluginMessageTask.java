package com.gmail.favorlock.bcpb.tasks;

import java.io.ByteArrayOutputStream;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.favorlock.bcpb.BungeeChatPlusBukkit;

public class PluginMessageTask extends BukkitRunnable {
	
	private final BungeeChatPlusBukkit plugin;
	private final Player player;
	private ByteArrayOutputStream bytes;
	
	public PluginMessageTask(BungeeChatPlusBukkit plugin, Player player, ByteArrayOutputStream bytes) {
		this.plugin = plugin;
		this.player = player;
		this.bytes = bytes;
	}

	public void run() {
		player.sendPluginMessage(this.plugin, "BungeeChatPlus", this.bytes.toByteArray());
	}

}
