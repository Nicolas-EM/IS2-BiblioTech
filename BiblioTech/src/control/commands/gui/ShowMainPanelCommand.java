package control.commands.gui;

import control.commands.Command;
import control.events.Event;
import gui.implementations.JPanelMain;

public class ShowMainPanelCommand extends Command {
	private static final Event id = Event.SHOW_MAINSCREEN;

	public ShowMainPanelCommand() {
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		new JPanelMain();
		return true;
	}
}
