package control.controller;

import control.events.Event;

public abstract class Controller {
	private static Controller instance;
	
	public static Controller getInstance() {
		if (instance == null) instance = new ControllerImp();
		return instance;
	}

	public abstract boolean action(Event e, Object lo);
}