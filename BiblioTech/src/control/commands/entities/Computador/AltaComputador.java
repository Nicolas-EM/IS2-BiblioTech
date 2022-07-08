package control.commands.entities.Computador;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Computador;
import model.transfers.TransferComputador;

public class AltaComputador extends Command {
	private static final Event id = Event.ALTA_COMPUTADOR;
	public AltaComputador() {
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		Computador l = new Computador();
		Pair<Event, String> response =l.alta((TransferComputador) o);
		Controller.getInstance().action(response.getKey(), response.getValue());
		Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Computador());
		return response.getKey() != Event.ERROR;
	}
}
