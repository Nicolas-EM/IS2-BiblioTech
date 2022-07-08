package control.commands.entities.Sala;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Sala;
import model.transfers.TransferSala;
public class SearchSala extends Command{

	private static final Event id = Event.BUSCAR_SALA;
	public SearchSala() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Object o) {
		Sala s= new Sala();
		@SuppressWarnings("unchecked")
		Pair<String, Event> pair = (Pair<String, Event>)o;
		TransferSala ts = s.get(pair.getKey());
		if (ts == null)
		{
			Controller.getInstance().action(Event.ERROR, "SALA NOT FOUND! D:");
			return false;
		}
		else
		{
			Pair<Sala,TransferSala>p = new Pair<Sala,TransferSala>(s, ts);
			Controller.getInstance().action(pair.getValue(), p);
		}
		return true;
	}

}
