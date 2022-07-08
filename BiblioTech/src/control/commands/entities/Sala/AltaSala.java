package control.commands.entities.Sala;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Sala;
import model.transfers.TransferSala;

public class AltaSala extends Command {
	private static final Event id = Event.ALTA_SALA;
	public AltaSala() {
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		Sala s = new Sala();
		Pair<Event, String> response = s.alta((TransferSala) o);
		Controller.getInstance().action(response.getKey(), response.getValue());
		Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Sala());
		return response.getKey() != Event.ERROR;
	}

}
