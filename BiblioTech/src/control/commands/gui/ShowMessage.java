package control.commands.gui;

import control.commands.Command;
import control.events.Event;
import model.services.GUIService;

public class ShowMessage extends Command{
	private static Event id = Event.MESSAGE;
	public ShowMessage() {
		super(id);
	}
	
	@Override
	public boolean execute(Object o) {
		GUIService.getInstance().showMessage(o.toString());
		return false;
	}
}