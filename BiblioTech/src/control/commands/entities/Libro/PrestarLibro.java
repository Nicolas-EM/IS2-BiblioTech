package control.commands.entities.Libro;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Libro;

public class PrestarLibro extends Command {

	private static final Event id = Event.PRESTAR;
	public PrestarLibro() {
		super(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Object o) {
		//Pair<Libro, TransferLibro> p = (Pair<Libro, TransferLibro>)o;
		Libro l = new Libro();
		Pair<String, Integer> res = (Pair<String, Integer>)o;
		Pair<Event, String> response = l.reservar(res.getKey(), Integer.toString(res.getValue()));
		if(response != null)
			Controller.getInstance().action(response.getKey(), response.getValue());
		Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Libro());
		return response.getKey() != Event.ERROR;
	}
}
