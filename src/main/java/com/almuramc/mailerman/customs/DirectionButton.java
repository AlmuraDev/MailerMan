/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almuramc.mailerman.customs;

import com.almuramc.mailerman.MailGUI;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

/**
 *
 * @author ZNickq
 */
public class DirectionButton extends GenericButton{
	private MailGUI gui;
	private int dir;
	
	public DirectionButton(MailGUI gui, int dir, String text) {
		super(text);
		this.gui = gui;
		this.dir = dir;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		gui.onDirection(dir);
	}
	
}
