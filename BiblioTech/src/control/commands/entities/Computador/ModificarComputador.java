package control.commands.entities.Computador;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Computador;
import model.transfers.TransferComputador;

public class ModificarComputador extends Command{

	private static final Event id = Event.MODIFICAR_COMPUTADOR;
	public ModificarComputador() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Object o) {
		Computador c = new Computador();
		Pair<Event, String> response = c.modificar((TransferComputador)o);
		Controller.getInstance().action(response.getKey(), response.getValue());
		return response.getKey() != Event.ERROR;
	}

}
