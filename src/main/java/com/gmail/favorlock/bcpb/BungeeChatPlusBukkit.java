package com.gmail.favorlock.bcpb;

import java.util.logging.Level;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.favorlock.bcpb.listeners.ChatListener;
import com.gmail.favorlock.bcpb.listeners.FactionChatListener;
import com.gmail.favorlock.bcpb.listeners.VaultListener;
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
			getServer().getPluginManager().registerEvents(new FactionChatListener(this), this);
		} else {
			getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		}
	}
	
    private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }
    
    public Chat getChat() {
    	return this.chat;
    }
    
    private void registerPluginChannels() {
    	Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeChatPlus");
    }
    
    private void registerListeners() {
    	getServer().getPluginManager().registerEvents(new VaultListener(this), this);
    }
    
	private void disable()
	{
		Bukkit.getPluginManager().disablePlugin(this);
	}
	
	public BungeeChatPlusBukkitConfig getBCPConfig() {
		return this.config;
	}
	
	public RegexManager getRegexManager() {
		return this.regex;
	}

}
