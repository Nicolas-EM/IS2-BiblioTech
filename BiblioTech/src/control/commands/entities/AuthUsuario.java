package control.commands.entities;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import model.dao.UsuarioDAO;
import model.services.GUIService;
import model.transfers.TransferUsuario;

public class AuthUsuario extends Command {
	private static final Event id = Event.AUTH_USER;

	public AuthUsuario() {
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		TransferUsuario usuario = (TransferUsuario) o;
		if (UsuarioDAO.getInstance().authenticate(usuario)) {
			GUIService.getInstance().setDefaultButton(null);//reset it
			return Controller.getInstance().action(Event.SHOW_MAINSCREEN, null);
		}
		else
			Controller.getInstance().action(Event.ERROR, "Authentication failed");
		return false;
	}

}
