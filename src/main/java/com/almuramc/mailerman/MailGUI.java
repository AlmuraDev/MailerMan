package com.almuramc.mailerman;

import com.almuramc.mailerman.customs.DirectionButton;
import com.almuramc.mailerman.customs.FixedTextField;
import com.almuramc.mailerman.customs.NSButton;
import com.almuramc.mailerman.customs.StateButton;
import java.util.List;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.player.SpoutPlayer;

public class MailGUI extends GenericPopup{
	private List<Message> isDisplaying;
	private int state, currentOne;
	private GenericTextField title = new FixedTextField();
	private GenericTextField description = new FixedTextField();
	private GenericTextField receiver = new FixedTextField();
	private GenericLabel time = new GenericLabel(), username = new GenericLabel();
	private GenericButton dname = new StateButton(this);
	private GenericButton ns = new NSButton(this);
	private MailerMan main;
	private SpoutPlayer player;
	private boolean inNewMode;

	public MailGUI(MailerMan main, SpoutPlayer who) {
		super();

		//Get which requests the opened window should show
		this.main = main;
		player = who;
		state = 0;
		inNewMode = false;

		//Create the widgets which are there every time
		GenericLabel usernameL = new GenericLabel("Sender: ");
		GenericLabel locationL = new GenericLabel("Receiver: ");
		GenericLabel timeL = new GenericLabel("Time: ");
		GenericLabel titleL = new GenericLabel("Title: ");
		GenericLabel descriptionL = new GenericLabel("Description: ");
		
		DirectionButton next = new DirectionButton(this, 1, ">");
		DirectionButton prev = new DirectionButton(this, -1, "<");
		DirectionButton close = new DirectionButton(this, 0, "Delete");

		usernameL.setX(30).setY(10).setHeight(15).setWidth(GenericLabel.getStringWidth(usernameL.getText()));
		locationL.setX(30).setY(25).setHeight(15).setWidth(GenericLabel.getStringWidth(locationL.getText()));
		timeL.setX(30).setY(40).setHeight(15).setWidth(GenericLabel.getStringWidth(timeL.getText()));
		titleL.setX(30).setY(55).setHeight(15).setWidth(GenericLabel.getStringWidth(titleL.getText()));
		descriptionL.setX(30).setY(90).setHeight(15).setWidth(GenericLabel.getStringWidth(descriptionL.getText()));

		username.setX(100).setY(10).setHeight(15).setWidth(80);
		receiver.setX(100).setY(23).setHeight(15).setWidth(80);
		time.setX(100).setY(40).setHeight(15).setWidth(80);
		title.setX(100).setY(55).setHeight(15).setWidth(80);
		description.setX(100).setY(90).setHeight(110).setWidth(300);
		dname.setX(300).setY(10).setHeight(15).setWidth(80);
		ns.setX(100).setY(210).setHeight(15).setWidth(50);
		next.setX(180).setY(210).setHeight(15).setWidth(15);
		prev.setX(160).setY(210).setHeight(15).setWidth(15);
		close.setX(210).setY(210).setHeight(15).setWidth(50);


		attachWidget(main, usernameL).attachWidget(main, timeL).attachWidget(main, titleL).attachWidget(main, locationL).attachWidget(main, titleL).attachWidget(main, descriptionL);
		attachWidget(main, username).attachWidget(main, time).attachWidget(main, receiver).attachWidget(main, title).attachWidget(main, description);
		attachWidget(main, dname);
		attachWidget(main, ns);
		attachWidget(main, close);
		attachWidget(main, next).attachWidget(main, prev);

		refreshForState();
		who.getMainScreen().attachPopupScreen(this);

	}

	private void refreshForState() {
		isDisplaying = main.getMessagesFor(player.getName(), state);

		switch (state) {
			case 0:
				dname.setText("Received");
				dname.setDirty(true);
				break;
			case 1:
				dname.setText("Sent");
				dname.setDirty(true);
				break;
		}
		
		currentOne = 0;
		time.setText("");
		receiver.setText("").setPlaceholder("").setDirty(true);
		username.setText("").setDirty(true);
		title.setText("").setPlaceholder("").setDirty(true);
		description.setText("").setPlaceholder("").setDirty(true);
		if(!(isDisplaying.isEmpty())) {
			updateCurrentPage();
		}

	}
	
	public void updateCurrentPage() {
		Message current = isDisplaying.get(currentOne);
		time.setText(current.getDate());
		receiver.setText(current.getReceiver());
		username.setText(current.getUsername());
		title.setText(current.getSubject());
		description.setText(current.getMessage());
		inNewMode = false;
		ns.setText("Create").setDirty(true);
	}

	public void onStateChange() {
		state++;
		if (state == 2) {
			state = 0;
		}
		refreshForState();
	}

	//String username, String receiver, String subject, String message
	
	public void onNS() {
		if(inNewMode) { //Saving
			inNewMode = false;
			Message fr = new Message(player.getName(), receiver.getText(), title.getText(), description.getText());
			main.addMessage(fr);
			refreshForState();
		} else { //Free stuff, get ready for editing
			time.setText("");
			username.setText("");
			receiver.setText("").setPlaceholder("Receiver here");
			title.setText("").setPlaceholder("Title here");
			description.setText("").setPlaceholder("Description here");
			inNewMode = true;
		}
	}

	public void onDirection(int dir) {
		if(dir == -1) {
			currentOne--;
			if(currentOne == -1) {
				currentOne++;
				return;
			} else {
				updateCurrentPage();
			}
		}
		if(dir == 0 && state != 1 && !isDisplaying.isEmpty()) {
			Message fr = isDisplaying.get(currentOne);
			main.deleteMessage(fr);
			refreshForState();
		}
		if(dir == 1) {
			currentOne++;
			if(currentOne > isDisplaying.size() - 1) {
				currentOne--;
				return;
			} else {
				updateCurrentPage();
			}
		}
	}
}
