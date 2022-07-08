package control.commands.entities.Libro;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Libro;
import model.transfers.TransferLibro;

public class SearchLibro extends Command {

	private static final Event id = Event.BUSCAR_LIBRO;
	public SearchLibro() {
		super(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Object o) {
		Libro l = new Libro();
		Pair<String, Event> pair = (Pair<String, Event>)o;
		TransferLibro tl = l.get(pair.getKey());
		if(tl == null) {
			Controller.getInstance().action(Event.ERROR, "BOOK NOT FOUND! D:");//el libro no existe
			return false;
		} else {
			Pair<Libro, TransferLibro> p = new Pair<Libro, TransferLibro>(l, tl);
			Controller.getInstance().action(pair.getValue(), p);//Event can be SHOW_MODIFICAR to modify or BAJA_
		}
		return true;
	}
}
