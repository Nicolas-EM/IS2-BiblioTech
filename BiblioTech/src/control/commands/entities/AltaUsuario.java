package control.commands.entities;

import javax.swing.SwingUtilities;

import control.UserPermissions;
import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import model.dao.UsuarioDAO;
import model.transfers.TransferUsuario;

public class AltaUsuario extends Command {
	private static final Event id = Event.ALTA_USUARIO;

	public AltaUsuario() {
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		if(!UsuarioDAO.getInstance().alta((TransferUsuario) o)) {
			Controller.getInstance().action(Event.ERROR, "Registration failed");
			return false;
		}
		else{
			Controller.getInstance().action(Event.MESSAGE, "Registration succesful");
			if(UsuarioDAO.getInstance().getPermissions() == UserPermissions.USER) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						 Controller.getInstance().action(Event.SHOW_AUTHENTICATION, null);
					}
				});
				return true;
			}
			else
				return Controller.getInstance().action(Event.UPDATE_GUI_MRP_USER, UsuarioDAO.getInstance());
		}
	}
}
