package com.gmail.favorlock.bcpb;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.gmail.favorlock.bcpb.listeners.ChatListener;
import com.gmail.favorlock.bcpb.listeners.FactionChatListener;
import com.gmail.favorlock.bcpb.listeners.VaultListener;
import com.gmail.favorlock.bcpb.tasks.PluginMessageTask;
import com.gmail.favorlock.bcpb.RegexManager;

public class BungeeChatPlusBukkit extends JavaPlugin {

	private Chat chat = null;
	private BungeeChatPlusBukkitConfig config;
	private RegexManager regex;

	public void onEnable() {
		try {
			config = new BungeeChatPlusBukkitConfig(this);
			config.init();
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "Failed to load the config!", e);
			disable();
		}
		if (config.Settings_EnableRegex) {
			// Initialize RegexManager
			regex = new RegexManager(this);
			// Load Regex Rules
			regex.loadRules();
		}
		if (config.Settings_VaultSupport) {
			if (setupChat()) {
				registerPluginChannels();
				registerListeners();
			} else {
				disable();
				return;
			}
		}

		if (config.Settings_FactionServer) {
			getServer().getPluginManager().registerEvents(
					new FactionChatListener(this), this);
		} else {
			getServer().getPluginManager().registerEvents(
					new ChatListener(this), this);
		}
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		String message = "";
		for (int x = 0; x < args.length; x++) {
			message += args[x] + " ";
		}
		message.trim();
		
		if (getBCPConfig().BroadcastToAllServers == true) {
			Player player = null;
			if (sender instanceof Player) {
				player = (Player) sender;
			} else {
				if (Bukkit.getOnlinePlayers().length > 0) {
					player = Bukkit.getOnlinePlayers()[0];
				} else {
					return false;
				}
			}

			if (command.getName().equalsIgnoreCase("say")) {
				ByteArrayOutputStream bStream = new ByteArrayOutputStream();
				DataOutputStream output = new DataOutputStream(bStream);
				try {
					output.writeUTF("Broadcast");
					output.writeUTF(message);
				} catch (IOException e) {
					e.printStackTrace();
				}

				new PluginMessageTask(this, player, bStream).runTask(this);
				return true;
			}
		} else {
			Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] " + message);
			return true;
		}
		return false;
	}

	public Chat getChat() {
		return this.chat;
	}

	private void registerPluginChannels() {
		Bukkit.getMessenger().registerOutgoingPluginChannel(this,
				"BungeeChatPlus");
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new VaultListener(this),
				this);
	}

	private void disable() {
		Bukkit.getPluginManager().disablePlugin(this);
	}

	public BungeeChatPlusBukkitConfig getBCPConfig() {
		return this.config;
	}

	public RegexManager getRegexManager() {
		return this.regex;
	}

}
