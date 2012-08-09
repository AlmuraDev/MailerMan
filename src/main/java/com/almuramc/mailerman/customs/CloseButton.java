/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almuramc.mailerman.customs;

import com.almuramc.mailerman.MailerMan;
import com.almuramc.mailerman.MainGUI;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class CloseButton extends GenericButton {

	private final boolean openMain;

	public CloseButton() {
		super("Close");
		openMain = false;
	}

	public CloseButton(boolean openMain) {
		super("Close");
		this.openMain = openMain;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		event.getPlayer().getMainScreen().closePopup();

		if (openMain) {
			new MainGUI((MailerMan) event.getButton().getPlugin(), event.getPlayer());
		}
	}
}
