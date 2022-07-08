package control.commands.entities.Libro;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Libro;
import model.transfers.TransferLibro;

public class BajaLibro extends Command {
	private static final Event id = Event.BAJA_LIBRO;
	public BajaLibro() {
		super(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Object o) {
		Pair<Libro, TransferLibro> p = (Pair<Libro, TransferLibro>)o;
		Pair<Event, String> response = p.getKey().baja(Integer.toString(p.getValue().getId()));
		Controller.getInstance().action(response.getKey(), response.getValue());
		Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Libro());
		return response.getKey() != Event.ERROR;
	}
}
