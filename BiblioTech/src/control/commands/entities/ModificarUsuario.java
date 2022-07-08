package control.commands.entities;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import model.dao.UsuarioDAO;
import model.transfers.TransferUsuario;

public class ModificarUsuario extends Command{
	private static final Event id = Event.MODIFICAR_USUARIO;

	public ModificarUsuario(){
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		if(UsuarioDAO.getInstance().modificar((TransferUsuario) o)) {
			Controller.getInstance().action(Event.MESSAGE, "Changed succesfully");
			return true;
		}
		Controller.getInstance().action(Event.ERROR, "Changes failed");
		return false;
	}
}
