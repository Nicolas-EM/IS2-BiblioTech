package control.controller;

import control.commands.CommandInterface;
import control.events.Event;

import control.commands.CommandFactory;

public class ControllerImp extends Controller {	
	
	@Override
	public boolean action(Event e, Object o) {
		CommandInterface com = CommandFactory.getInstance().getCommand(e); /* Pedimos el comando a la factoria */

		/*
		 * Si el comando existe se ejecuta, si no puede ser que sea un get de una vista
		 * o que el evento no sea conocido
		 */
		if (com != null) {
			return com.execute(o);
		}
		else {
			System.err.println(e.toString() + ": Command not found, add it to the list in control.commands.CommandFactoryImp!");
			return false;
		}
	}
}