package control.commands.gui;

import java.util.List;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import model.dao.items.Libro;

public class ShowListaLibros extends Command {

	private static final Event id = Event.LISTAR_LIBROS;
	public ShowListaLibros() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Object o) {
		Libro l = new Libro();
		List<String> idList  = l.getAllID();
		for(String id : idList)
			Controller.getInstance().action(Event.MOSTRAR_LIBRO, id);
		return false;
	}

}
