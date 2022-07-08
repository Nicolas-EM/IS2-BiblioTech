package control.commands;

import control.events.Event;

public abstract class CommandFactory {
	private static CommandFactory instance;

	public static CommandFactory getInstance() {
		if (instance == null) instance = new CommandFactoryImp();
		return instance;
	}

	public abstract CommandInterface getCommand(Event e);
}
