package control.commands.entities.Sala;

import java.util.List;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import model.dao.items.Sala;

public class Show_ListaSalas extends Command{

	private static final Event id = Event.LISTAR_SALAS;
	public Show_ListaSalas() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Object o) {
		Sala s= new Sala();
		List<String> idList  = s.getAllID();
		for(String id : idList)
			Controller.getInstance().action(Event.MOSTRAR_SALA, id);
		return false;
	}

}
