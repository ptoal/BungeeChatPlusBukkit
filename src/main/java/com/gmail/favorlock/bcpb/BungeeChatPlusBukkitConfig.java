package com.gmail.favorlock.bcpb;

import java.io.File;
import com.gmail.favorlock.bcpb.config.Config;

public class BungeeChatPlusBukkitConfig extends Config {
	
	public BungeeChatPlusBukkitConfig(BungeeChatPlusBukkit plugin) {
		CONFIG_FILE = new File("plugins" + File.separator + plugin.getDescription().getName(), "config.yml");
		CONFIG_HEADER = "BungeeChatPlusBukkit - By Favorlock";
	}
	
	public boolean Settings_VaultSupport = true;
	public boolean Settings_FactionServer = false;

}
