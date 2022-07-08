package control.commands.entities.Computador;

import java.util.List;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import model.dao.items.Computador;

public class Show_ListaPC extends Command{

	private static final Event id = Event.LISTAR_PCS;
	public Show_ListaPC() {
		super(id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean execute(Object o) {
		Computador c= new Computador();
		List<String> idList  = c.getAllID();
		for(String id : idList)
			Controller.getInstance().action(Event.MOSTRAR_PC, id);
		return false;
	}

}
