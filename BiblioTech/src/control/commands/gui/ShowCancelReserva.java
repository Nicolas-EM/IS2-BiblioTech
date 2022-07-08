package control.commands.gui;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.SimulatedObject;
import model.dao.UsuarioDAO;
import model.transfers.Transfer;

public class ShowCancelReserva extends Command {
	public static final Event id = Event.SHOW_CANCEL_RESERVA;
	public ShowCancelReserva() {
		super(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Object o) {
		Pair<SimulatedObject, Transfer> p = (Pair<SimulatedObject, Transfer>)o;
		//new JPanelReserva(p.getKey(), p.getKey().getEventCancelarReserva(), p.getValue().getId());//ask for dni
		
		//we use current user's dni
		Pair<String, Integer> res = new Pair<String, Integer>(UsuarioDAO.getInstance().getDni(), p.getValue().getId());
		Controller.getInstance().action(p.getKey().getEventCancelarReserva(), res);
		return false;
	}
}
