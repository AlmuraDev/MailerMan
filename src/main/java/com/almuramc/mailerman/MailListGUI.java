/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almuramc.mailerman;

import com.almuramc.mailerman.customs.CloseButton;
import com.almuramc.mailerman.customs.DirectionButton;
import java.util.List;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericListWidget;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.ListWidgetItem;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 *
 * @author ZNickq
 */
public class MailListGUI extends GenericPopup {

	private MailerMan main;
	private SpoutPlayer who;
	private boolean received;
	private List<Message> isDisplaying;
	private GenericListWidget gle = new GenericListWidget();

	public MailListGUI(MailerMan main, SpoutPlayer who, boolean received) {
		this.main = main;
		this.who = who;
		this.received = received;

		gle.setAnchor(WidgetAnchor.CENTER_CENTER);
		gle.shiftXPos(-190).shiftYPos(-110);
		gle.setHeight(200).setWidth(400);

		
		//Set the background
		GenericTexture border = new GenericTexture("http://www.pixentral.com/pics/1duZT49LzMnodP53SIPGIqZ8xdKS.png");
		border.setAnchor(WidgetAnchor.CENTER_CENTER);
		border.setPriority(RenderPriority.High);
		border.setWidth(420).setHeight(345);
		border.shiftXPos(-205).shiftYPos(-120);
		
		GenericLabel gl = new GenericLabel("List of Mail");
		gl.setScale(1.2F);
		gl.setAnchor(WidgetAnchor.CENTER_CENTER);
		gl.setHeight(15).setWidth(GenericLabel.getStringWidth(gl.getText()));
		gl.shiftXPos(-25).shiftYPos(-105);
		
		GenericButton view = new DirectionButton(this, 0, "View");
		view.setAnchor(WidgetAnchor.CENTER_CENTER);
		view.shiftXPos(-190).shiftYPos(95);
		view.setHeight(15).setWidth(50);
		
		GenericButton reply = new DirectionButton(this, 1, "Reply");
		reply.setAnchor(WidgetAnchor.CENTER_CENTER);
		reply.shiftXPos(-130).shiftYPos(95);
		reply.setHeight(15).setWidth(60);
		
		GenericButton delete = new DirectionButton(this, 2, "Delete");
		delete.setAnchor(WidgetAnchor.CENTER_CENTER);
		delete.shiftXPos(-70).shiftYPos(95);
		delete.setHeight(15).setWidth(60);
		
		CloseButton close = new CloseButton(true);
		close.setAnchor(WidgetAnchor.CENTER_CENTER);
		close.shiftXPos(150).shiftYPos(95);
		close.setHeight(15).setWidth(50);

		attachWidget(main, gl);
		attachWidget(main, gle).attachWidget(main, view).attachWidget(main, border);
		attachWidget(main, close).attachWidget(main, reply).attachWidget(main, delete);
		
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
		if (dir == 1) {
			new ViewGUI(main, who, cur, true);
		}
		if (dir == 2) {
			main.deleteMessage(cur);
			refreshForContent();
		}
	}
}
