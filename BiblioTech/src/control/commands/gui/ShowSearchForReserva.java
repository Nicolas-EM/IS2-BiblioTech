package control.commands.gui;

import control.commands.Command;
import control.events.Event;
import gui.implementations.JPanelSearch;
import model.dao.SimulatedObject;

public class ShowSearchForReserva extends Command {

	public static final Event id = Event.SHOW_SEARCH_FOR_RESERVATION;
	public ShowSearchForReserva() {
		super(id);
	}
	
	@Override
	public boolean execute(Object _o) {
		SimulatedObject o = (SimulatedObject)_o;
		new JPanelSearch(o, Event.SHOW_RESERVA);
		return false;
	}
}
