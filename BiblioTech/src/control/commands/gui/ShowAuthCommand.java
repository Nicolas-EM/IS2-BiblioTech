package control.commands.gui;

import control.commands.Command;
import control.events.Event;
import gui.implementations.JPanelAuth;

public class ShowAuthCommand extends Command {
	private static Event id = Event.SHOW_AUTHENTICATION;

	public ShowAuthCommand() {
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		new JPanelAuth();
		return true;
	}
}
