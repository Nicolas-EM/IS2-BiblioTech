package control.commands.entities.Sala;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Sala;

public class DelReservaSala extends Command{

	private static final Event id = Event.DEL_RESERVA_SALA;
	public DelReservaSala() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Object o) {
		Sala s = new Sala();
		@SuppressWarnings("unchecked")
		Pair<String, Integer> res = (Pair<String, Integer>)o;
		Pair<Event, String> response = s.cancelar_reserva(res.getKey(), Integer.toString(res.getValue()));
		if(response != null)
			Controller.getInstance().action(response.getKey(), response.getValue());
		Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Sala());
		return response.getKey() != Event.ERROR;
	}

}
