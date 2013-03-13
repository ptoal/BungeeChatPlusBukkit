package com.gmail.favorlock.bcpb;

import java.util.logging.Level;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.favorlock.bcpb.listeners.ChatListener;
import com.gmail.favorlock.bcpb.listeners.VaultListener;

public class BungeeChatPlusBukkit extends JavaPlugin {
	
	private Chat chat = null;
	private BungeeChatPlusBukkitConfig config;
	
	public void onEnable() {
		try {
			config = new BungeeChatPlusBukkitConfig(this);
			config.init();
			getLogger().log(Level.INFO, "BCBP Info: " + config.Settings_VaultSupport + ", " + config.Settings_FactionServer);
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "Failed to load the config!", e);
			disable();
		}
		
		if (config.Settings_VaultSupport) {
			if (setupChat()) {
				registerPluginChannels();
				registerListeners();
			} else {
				disable();
			}
		}
		if (config.Settings_FactionServer) {
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

}
