package com.almuramc.mailerman;

import com.almuramc.mailerman.customs.FixedTextField;
import com.almuramc.mailerman.customs.NSButton;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class ViewGUI extends GenericPopup {

	private Message isDisplaying;
	private GenericTextField title = new FixedTextField();
	private GenericTextField description = new FixedTextField();
	private GenericTextField receiver = new FixedTextField();
	private GenericLabel time = new GenericLabel(), username = new GenericLabel();
	private GenericButton ns = new NSButton(this);
	private MailerMan main;
	private SpoutPlayer player;

	public ViewGUI(MailerMan main, SpoutPlayer who, Message toDisplay) {
		super();

		//Get which requests the opened window should show
		this.main = main;
		this.isDisplaying = toDisplay;
		player = who;
		
		//Prepare the background
		GenericTexture border = new GenericTexture("http://www.pixentral.com/pics/1duZT49LzMnodP53SIPGIqZ8xdKS.png");
		border.setAnchor(WidgetAnchor.CENTER_CENTER);
		border.setPriority(RenderPriority.High);
		border.setWidth(626).setHeight(240);
		border.shiftXPos(-213).shiftYPos(-118);

		//Create the widgets which are there every time
		GenericLabel usernameL = new GenericLabel("Sender: ");
		GenericLabel locationL = new GenericLabel("Receiver: ");
		GenericLabel timeL = new GenericLabel("Time: ");
		GenericLabel titleL = new GenericLabel("Title: ");
		GenericLabel descriptionL = new GenericLabel("Description: ");

		usernameL.setAnchor(WidgetAnchor.CENTER_CENTER);
		usernameL.setHeight(15).setWidth(GenericLabel.getStringWidth(usernameL.getText()));
		usernameL.shiftXPos(-190).shiftYPos(-100);

		locationL.setAnchor(WidgetAnchor.CENTER_CENTER);
		locationL.setHeight(15).setWidth(GenericLabel.getStringWidth(locationL.getText()));
		locationL.shiftXPos(-190).shiftYPos(-85);

		timeL.setHeight(15).setWidth(GenericLabel.getStringWidth(timeL.getText()));
		timeL.setAnchor(WidgetAnchor.CENTER_CENTER);
		timeL.shiftXPos(-190).shiftYPos(-70);

		titleL.setHeight(15).setWidth(GenericLabel.getStringWidth(titleL.getText()));
		titleL.setAnchor(WidgetAnchor.CENTER_CENTER);
		titleL.shiftXPos(-190).shiftYPos(-55);

		descriptionL.setHeight(15).setWidth(GenericLabel.getStringWidth(descriptionL.getText()));
		descriptionL.setAnchor(WidgetAnchor.CENTER_CENTER);
		descriptionL.shiftXPos(-190).shiftYPos(-35);


		username.setHeight(15).setWidth(80);
		username.setAnchor(WidgetAnchor.CENTER_CENTER);
		username.shiftXPos(-125).shiftYPos(-100);

		receiver.setHeight(15).setWidth(80);
		receiver.setAnchor(WidgetAnchor.CENTER_CENTER);
		receiver.shiftXPos(-125).shiftYPos(-88);

		time.setHeight(15).setWidth(80);
		time.setAnchor(WidgetAnchor.CENTER_CENTER);
		time.shiftXPos(-125).shiftYPos(-70);

		title.setHeight(15).setWidth(80);
		title.setAnchor(WidgetAnchor.CENTER_CENTER);
		title.shiftXPos(-125).shiftYPos(-58);

		description.setAnchor(WidgetAnchor.CENTER_CENTER);
		description.setMaximumLines(9);
		description.setMaximumCharacters(1000);
		description.setHeight(110).setWidth(300);
		description.shiftXPos(-125).shiftYPos(-35);

		ns.setHeight(15).setWidth(50);
		ns.setAnchor(WidgetAnchor.CENTER_CENTER);
		ns.shiftXPos(-125).shiftYPos(85);



		attachWidget(main, border);
		attachWidget(main, usernameL).attachWidget(main, timeL).attachWidget(main, titleL).attachWidget(main, locationL).attachWidget(main, titleL).attachWidget(main, descriptionL);
		attachWidget(main, username).attachWidget(main, time).attachWidget(main, receiver).attachWidget(main, title).attachWidget(main, description);
		attachWidget(main, ns);

		refreshForState();
		who.getMainScreen().closePopup();
		who.getMainScreen().attachPopupScreen(this);

	}

	private void refreshForState() {

		time.setText("");
		receiver.setText("").setPlaceholder("Receiver here").setDirty(true);
		username.setText("").setDirty(true);
		title.setText("").setPlaceholder("Title here").setDirty(true);
		description.setText("").setPlaceholder("Description here").setDirty(true);
		if (isDisplaying != null) {
			updateCurrentPage();
		}

	}

	public void updateCurrentPage() {
		time.setText(isDisplaying.getDate());
		receiver.setText(isDisplaying.getReceiver());
		username.setText(isDisplaying.getUsername());
		title.setText(isDisplaying.getSubject());
		description.setText(isDisplaying.getMessage());
		ns.setText("Reply").setDirty(true);
	}

	//String username, String receiver, String subject, String message
	public void onNS() {
		if (isDisplaying == null) {
			Message fr = new Message(player.getName(), receiver.getText(), title.getText(), description.getText());
			main.addMessage(fr);
			player.getMainScreen().closePopup();
			player.getMainScreen().attachPopupScreen(new MailListGUI(main, player, false));
		} else {
			String toWhom = getOtherPerson();
			isDisplaying = null;
			refreshForState();
			receiver.setText(toWhom);
			ns.setText("Send").setDirty(true);
		}

	}

	private String getOtherPerson() {
		if(username.getText().equalsIgnoreCase(player.getName())) {
			return receiver.getText();
		}
		return username.getText();
	}
}
