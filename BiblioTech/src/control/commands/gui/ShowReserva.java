package control.commands.gui;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.SimulatedObject;
import model.dao.UsuarioDAO;
import model.transfers.Transfer;

public class ShowReserva extends Command {
	public static final Event id = Event.SHOW_RESERVA;
	public ShowReserva() {
		super(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Object o) {
		Pair<SimulatedObject, Transfer> p = (Pair<SimulatedObject, Transfer>)o;
		//new JPanelReserva(p.getKey(), p.getKey().getEventReservar(), p.getValue().getId());//ask for dni
		
		//we now use current user's dni
		Pair<String, Integer> res = new Pair<String, Integer>(UsuarioDAO.getInstance().getDni(), p.getValue().getId());
		Controller.getInstance().action(p.getKey().getEventReservar(), res);
		return false;
	}
}
