package control.commands.gui;

import java.util.List;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import model.dao.UsuarioDAO;

public class ShowUsuarioList extends Command {
	private static final Event id = Event.LISTAR_USUARIOS;
	
	public ShowUsuarioList() {
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		List<String> dniList = UsuarioDAO.getInstance().getAllDNI();
		for(String dni : dniList) {
			Controller.getInstance().action(Event.MOSTRAR_USUARIO, dni);
		}
		return false;
	}

}
