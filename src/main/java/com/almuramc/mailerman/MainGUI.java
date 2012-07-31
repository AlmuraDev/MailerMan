/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almuramc.mailerman;

import com.almuramc.mailerman.customs.CloseButton;
import com.almuramc.mailerman.customs.DirectionButton;
import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 *
 * @author ZNickq
 */
public class MainGUI extends GenericPopup {

	private MailerMan main;
	private SpoutPlayer who;

	public MainGUI(MailerMan main, SpoutPlayer who) {
		this.main = main;
		this.who = who;

		
		//Set the background
		GenericTexture border = new GenericTexture("http://www.pixentral.com/pics/1duZT49LzMnodP53SIPGIqZ8xdKS.png");
		border.setAnchor(WidgetAnchor.CENTER_CENTER);
		border.setPriority(RenderPriority.High);
		border.setWidth(170).setHeight(120);
		border.shiftXPos(-85).shiftYPos(-40);
		
		GenericLabel gl = new GenericLabel("Welcome to MailerMan");
		gl.setAnchor(WidgetAnchor.CENTER_CENTER);
		gl.setScale(1.2F);
		gl.setHeight(15).setWidth(120);
		gl.shiftXPos(-60).shiftYPos(-30);

		GenericButton create = new DirectionButton(this, 1, "Compose new mail");
		GenericButton viewown = new DirectionButton(this, 2, "Read mail");
		GenericButton viewother = new DirectionButton(this, 3, "View sent items");
		CloseButton close = new CloseButton();

		create.setAnchor(WidgetAnchor.CENTER_CENTER);
		viewown.setAnchor(WidgetAnchor.CENTER_CENTER);
		viewother.setAnchor(WidgetAnchor.CENTER_CENTER);
		close.setAnchor(WidgetAnchor.CENTER_CENTER);

		create.setHeight(16).setWidth(100).shiftXPos(-50).shiftYPos(-10);
		viewown.setHeight(16).setWidth(100).shiftXPos(-50).shiftYPos(10);
		viewother.setHeight(16).setWidth(100).shiftXPos(-50).shiftYPos(30);
		close.setHeight(16).setWidth(100).shiftXPos(-50).shiftYPos(50);

		attachWidget(main, border);
		attachWidget(main, close);
		attachWidget(main, gl).attachWidget(main, create).attachWidget(main, viewown);

		if (who.hasPermission("mailerman.admin") || who.isOp()) {
			attachWidget(main, viewother);
		}

		who.getMainScreen().attachPopupScreen(this);
	}

	public void onDirection(int dir) {
		switch (dir) {
			case 1: //Create
				if(!(who.hasPermission("mailerman.create"))) {
					who.sendMessage(ChatColor.RED+"You are not allowed to create messages!");
					return;
				}
				new ViewGUI(main, who, null); //sending null, means create message
				break;
			case 2: //Edit
				if(!(who.hasPermission("mailerman.use"))) {
					who.sendMessage(ChatColor.RED+"You are not allowed to view messages!");
					return;
				}
				new MailListGUI(main, who, true);
				break;
			case 3: //View
				new MailListGUI(main, who, false);
				break;
		}
	}
}
