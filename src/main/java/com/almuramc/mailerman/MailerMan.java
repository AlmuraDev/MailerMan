package com.almuramc.mailerman;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;

public class MailerMan extends JavaPlugin implements Listener{
	private List<Message> allMessages = new ArrayList<Message>();

	@Override
	public void onDisable() {
		getDataFolder().mkdir();
		try {
			SLAPI.save(allMessages, getDataFolder() + File.separator + "data.dat");
		} catch (Exception ex) {
			Logger.getLogger(MailerMan.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void onEnable() {
		try {
			allMessages = (List<Message>) SLAPI.load(getDataFolder() + File.separator + "data.dat");
		} catch (Exception ex) {
		}
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
	}
	
	@EventHandler
	public void onKeyPressed(KeyPressedEvent event) {
		if(event.getKey() == Keyboard.KEY_F9) {
			new MainGUI(this, event.getPlayer());
		}
	}

	/**
	 * States:
	 * 0 - received
	 * 1 - sent
	 */
	public List<Message> getMessagesFor(String name, boolean received) {
		List<Message> toRet = new ArrayList<Message>();
		for(int i = allMessages.size() - 1;i>=0;i--) {
			Message msg = allMessages.get(i);
			if(received && msg.getReceiver().equals(name)) {
				toRet.add(msg);
			}
			if(!received && msg.getUsername().equals(name)) {
				toRet.add(msg);
			}
		}
		return toRet;
	}

	public void addMessage(Message fr) {
		allMessages.add(fr);
		String receiver = fr.getReceiver();
		Player on = Bukkit.getPlayer(receiver);
		if(on != null) {
			on.sendMessage(ChatColor.GREEN+"You got new mail!");
		}
	}

	public void deleteMessage(Message fr) {
		allMessages.remove(fr);
	}
	
}
