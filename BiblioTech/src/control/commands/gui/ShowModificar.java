package control.commands.gui;

import control.commands.Command;
import control.events.Event;
import gui.implementations.JPanelModify;
import gui.utils.Pair;
import model.dao.SimulatedObject;
import model.transfers.Transfer;

public class ShowModificar extends Command {
	public static final Event id = Event.SHOW_MODIFICAR;
	public ShowModificar() {
		super(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Object o) {
		
		Pair<SimulatedObject, Transfer> p = (Pair<SimulatedObject, Transfer>)o;
		JPanelModify panel = new JPanelModify((SimulatedObject)p.getKey(), p.getValue().getId());
		panel.setTextFieldData(p.getValue().toList());//set data from transfer to textfields
		return false;
	}
}
