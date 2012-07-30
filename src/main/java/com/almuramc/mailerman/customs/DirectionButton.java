/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almuramc.mailerman.customs;

import com.almuramc.mailerman.MailListGUI;
import com.almuramc.mailerman.MainGUI;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

/**
 *
 * @author ZNickq
 */
public class DirectionButton extends GenericButton{
	private Object gui;
	private int dir;
	
	public DirectionButton(Object gui, int dir, String text) {
		super(text);
		this.gui = gui;
		this.dir = dir;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		if(gui instanceof MainGUI) {
			((MainGUI)gui).onDirection(dir);
		}
		if(gui instanceof MailListGUI) {
			((MailListGUI)gui).onDirection(dir);
		}
	}
	
}
