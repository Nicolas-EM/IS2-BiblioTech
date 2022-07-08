package control.commands.gui;

import control.commands.Command;
import control.events.Event;
import gui.implementations.JPanelUsuarioModificar;

public class ShowModificarUsuario extends Command {
	private static final Event id = Event.SHOW_MODIFICAR_USUARIO;
	
	public ShowModificarUsuario() {
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		new JPanelUsuarioModificar();
		return false;
	}
}
