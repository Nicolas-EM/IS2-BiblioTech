package control.commands;

import control.events.Event;

public abstract interface CommandInterface {
	public abstract boolean execute(Object o);
	public abstract Event getId();
}