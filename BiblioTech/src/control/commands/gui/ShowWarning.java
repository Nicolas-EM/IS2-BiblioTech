package control.commands.gui;

import control.commands.Command;
import control.events.Event;
import model.services.GUIService;

public class ShowWarning extends Command{
	private static Event id = Event.WARNING;
	public ShowWarning() {
		super(id);
	}
	
	@Override
	public boolean execute(Object o) {
		GUIService.getInstance().showWarning(o.toString());
		return false;
	}
}