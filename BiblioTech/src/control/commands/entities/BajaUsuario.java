package control.commands.entities;

import javax.swing.SwingUtilities;

import control.UserPermissions;
import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import model.dao.UsuarioDAO;
import model.services.GUIService;
import model.transfers.TransferUsuario;

public class BajaUsuario extends Command {
	private static final Event id = Event.BAJA_USUARIO;

	public BajaUsuario() {
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		if(UsuarioDAO.getInstance().getPermissions() == UserPermissions.USER) {
			if(UsuarioDAO.getInstance().baja(null)) {
				UsuarioDAO.getInstance().logout();
				
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						Controller.getInstance().action(Event.SHOW_AUTHENTICATION, null);
					}
				});
				
				return true;
			}
		}
		else {
			String dni = null;
			
			// Para testing:
			try {
				if((TransferUsuario) o != null)
					dni = ((TransferUsuario) o).getDni();
			}catch(Exception e) {}
			
			if(dni == null) {
				dni = GUIService.getInstance().getInput("Enter DNI");
			}

			TransferUsuario user = new TransferUsuario(dni);
			if(UsuarioDAO.getInstance().baja(user)) {
				Controller.getInstance().action(Event.MESSAGE, "User deleted!");
				
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						Controller.getInstance().action(Event.UPDATE_GUI_MRP_USER, UsuarioDAO.getInstance());
					}
				});
				
				return true;
			}
			else
				Controller.getInstance().action(Event.ERROR, "User deletion failed!");
		}
		
		return false;
	}
}