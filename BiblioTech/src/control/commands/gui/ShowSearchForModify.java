package control.commands.gui;

import control.commands.Command;
import control.events.Event;
import gui.implementations.JPanelSearch;
import model.dao.SimulatedObject;

public class ShowSearchForModify extends Command {

	public static final Event id = Event.SHOW_SEARCH_FOR_MODIFY;
	public ShowSearchForModify() {
		super(id);
	}
	
	@Override
	public boolean execute(Object o) {
		new JPanelSearch((SimulatedObject)o, Event.SHOW_MODIFICAR);
		return false;
	}

}
