package control.commands.entities.Sala;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Sala;
import model.transfers.TransferSala;

public class ModificarSala extends Command{

	private static final Event id = Event.MODIFICAR_SALA;
	public ModificarSala() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Object o) {
		Sala s = new Sala();
		Pair<Event, String> response = s.modificar((TransferSala)o);
		Controller.getInstance().action(response.getKey(), response.getValue());
		Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Sala());
		return response.getKey() != Event.ERROR;
	}

}
