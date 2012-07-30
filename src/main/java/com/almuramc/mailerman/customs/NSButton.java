/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almuramc.mailerman.customs;

import com.almuramc.mailerman.ViewGUI;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

/**
 *
 * @author ZNickq
 */
public class NSButton extends GenericButton {

	private ViewGUI gui;

	public NSButton(ViewGUI gui) {
		super("Send");
		this.gui = gui;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		gui.onNS();
	}
	
}
