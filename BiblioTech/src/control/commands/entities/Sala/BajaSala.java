package control.commands.entities.Sala;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Sala;
import model.transfers.TransferSala;

public class BajaSala extends Command{

	private static final Event id = Event.BAJA_SALA;
	public BajaSala() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Object o) {
		@SuppressWarnings("unchecked")
		Pair<Sala,TransferSala>p = (Pair<Sala,TransferSala>)o;
		Pair<Event, String> response = p.getKey().baja(Integer.toString(p.getValue().getId()));
		Controller.getInstance().action(response.getKey(), response.getValue());
		Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Sala());
		return response.getKey() != Event.ERROR;
	}

}
