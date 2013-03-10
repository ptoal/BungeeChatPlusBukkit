package com.gmail.favorlock.bcpb;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.favorlock.bcpb.listeners.VaultListener;

public class BungeeChatPlusBukkit extends JavaPlugin implements Listener {
	
	private Chat chat = null;
	
	public void onEnable() {
		if (setupChat()) {
			registerPluginChannels();
			registerListeners();
		} else {
			disable();
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
