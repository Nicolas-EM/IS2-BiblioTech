package control.commands.entities.Computador;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Computador;
import model.transfers.TransferComputador;

public class SearchComputador extends Command {
	private static final Event id = Event.BUSCAR_COMPUTADOR;
	public SearchComputador() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Object o) {
		Computador c = new Computador();
		@SuppressWarnings("unchecked")
		Pair<String, Event> pair = (Pair<String, Event>)o;
		TransferComputador tc = c.get(pair.getKey());
		if (tc == null)
		{
			Controller.getInstance().action(Event.ERROR, "Computer NOT FOUND! D:");
			return false;
		}
		else
		{
			Pair<Computador, TransferComputador>p = new Pair<Computador, TransferComputador>(c, tc);
			Controller.getInstance().action(pair.getValue(), p);
		}
		return true;
	}

}
