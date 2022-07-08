package control.commands.entities.Computador;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Computador;
import model.transfers.TransferComputador;

public class BajaComputador extends Command{
	private static final Event id = Event.BAJA_COMPUTADOR;
	public BajaComputador() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Object o) {
		Pair<Computador, TransferComputador> p = (Pair<Computador, TransferComputador>)o;
		Pair<Event, String> response = p.getKey().baja(Integer.toString(p.getValue().getId()));
		Controller.getInstance().action(response.getKey(), response.getValue());
		Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Computador());
		return response.getKey() != Event.ERROR;	
	}

}
