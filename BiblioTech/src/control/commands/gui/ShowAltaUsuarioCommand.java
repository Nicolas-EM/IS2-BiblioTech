package control.commands.gui;

import control.commands.Command;
import control.events.Event;
import gui.implementations.JPanelRegister;

public class ShowAltaUsuarioCommand extends Command {
	private static final Event id = Event.SHOW_ALTA_USUARIO;
	
	public ShowAltaUsuarioCommand() {
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		new JPanelRegister();
		return false;
	}
}
