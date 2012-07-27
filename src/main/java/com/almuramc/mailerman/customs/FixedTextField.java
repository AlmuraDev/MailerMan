package com.almuramc.mailerman.customs;

import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.GenericTextField;

public class FixedTextField extends GenericTextField{

	@Override
	public void onTextFieldChange(TextFieldChangeEvent event) {
		setPlaceholder("").setDirty(true);
	}

}
