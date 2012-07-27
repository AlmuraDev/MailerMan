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
public class StateButton extends GenericButton{
	private MailGUI gui;
	
	public StateButton(MailGUI gui) {
		super();
		this.gui = gui;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		gui.onStateChange();
	}
	
	
}
