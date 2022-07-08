package control.commands.entities.Libro;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Libro;
import model.transfers.TransferLibro;

public class AltaLibro extends Command {
	private static final Event id = Event.ALTA_LIBRO;
	public AltaLibro() {
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		Libro l = new Libro();
		Pair<Event, String> response = l.alta((TransferLibro) o);
		Controller.getInstance().action(response.getKey(), response.getValue());
		Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Libro());
		return response.getKey() != Event.ERROR;//returns true if no error
	}
}
