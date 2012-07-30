/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almuramc.mailerman;

import com.almuramc.mailerman.customs.DirectionButton;
import java.util.Arrays;
import java.util.List;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericComboBox;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericListWidget;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.ListWidgetItem;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 *
 * @author ZNickq
 */
public class MailListGUI extends GenericPopup{
	private MailerMan main;
	private SpoutPlayer who;
	private boolean received;
	private int state;
	private List<Message> isDisplaying;
	private GenericListWidget gle = new GenericListWidget();

	public MailListGUI(MailerMan main, SpoutPlayer who, boolean received) {
		this.main = main;
		this.who = who;
		this.received = received;
		this.state = 0;

		gle.setAnchor(WidgetAnchor.CENTER_CENTER);
		gle.shiftXPos(-190).shiftYPos(-100);
		gle.setHeight(200).setWidth(400);

		GenericButton view = new DirectionButton(this, 0, "View");
		view.setAnchor(WidgetAnchor.CENTER_CENTER);
		view.shiftXPos(-190).shiftYPos(100);
		view.setHeight(15).setWidth(GenericLabel.getStringWidth("View") + 10);

		attachWidget(main, gle).attachWidget(main, view);

		refreshForContent();

		who.getMainScreen().closePopup();
		who.getMainScreen().attachPopupScreen(this);
	}

	private void refreshForContent() {
			isDisplaying = main.getMessagesFor(who.getName(), received);
		gle.clear();
		for (Message fre : isDisplaying) {
			gle.addItem(new ListWidgetItem(fre.getSubject() + " - by " + fre.getUsername(), fre.getMessage()));
		}
	}

	public void onDirection(int dir) {

		Message cur = null;
		try {
			cur = isDisplaying.get(gle.getSelectedRow());
		} catch (Exception ex) {
		}
		if (cur == null) {
			return;
		}
		if (dir == 0) { //view
			new ViewGUI(main, who, cur); //cur is not null, display it
		}
	}

	public void onSelectionChanged(String text) {
		if(text == null) {
			return;
		}
		if (text.equals("Opened")) {
			state = 0;
			refreshForContent();
		} else {
			state = 1;
			refreshForContent();
		}
	}
}
