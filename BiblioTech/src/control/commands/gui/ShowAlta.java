package control.commands.gui;

import control.commands.Command;
import control.events.Event;
import gui.implementations.JPanelAlta;
import model.dao.SimulatedObject;

public class ShowAlta extends Command {
	public static final Event id = Event.SHOW_ALTA;
	public ShowAlta() {
		super(id);
	}
	
	@Override
	public boolean execute(Object o) {
		//System.out.println("Command ShowAlta: " + o.toString());
		new JPanelAlta((SimulatedObject)o);
		return false;
	}
}
