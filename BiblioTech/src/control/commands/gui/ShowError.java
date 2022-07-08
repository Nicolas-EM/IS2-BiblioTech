package control.commands.gui;

import control.commands.Command;
import control.events.Event;
import model.services.GUIService;

public class ShowError extends Command{
	private static Event id = Event.ERROR;
	public ShowError() {
		super(id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean execute(Object o) {
		GUIService.getInstance().showError(o.toString());
		return false;
	}
}