package control.commands.entities.Computador;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Computador;

public class ReservaComputador  extends Command {
	private static final Event id = Event.CREAR_RESERVA_COMPUTADOR;
	public ReservaComputador() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Object o) {
		Computador c = new Computador();
		@SuppressWarnings("unchecked")
		Pair<String, Integer> res = (Pair<String, Integer>)o;
		Pair<Event, String> response = c.reservar(res.getKey(), Integer.toString(res.getValue()));
		if(response != null)
			Controller.getInstance().action(response.getKey(), response.getValue());
		Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Computador());
		return response.getKey() != Event.ERROR;
	}

}
