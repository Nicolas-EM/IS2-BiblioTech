package main;

import control.controller.Controller;
import control.events.Event;

public class BiblioTech {

	public static void main(String[] args) {
		Controller.getInstance().action(Event.SHOW_AUTHENTICATION, null);
	}
}
