package control.commands;

import control.events.Event;

public abstract class Command implements CommandInterface {
	private Event id;
	
	public Command(Event id) {
		this.id = id;
	}

	@Override
	public abstract boolean execute(Object o);
	
	@Override
	public Event getId() {
		return this.id;
	}
}
