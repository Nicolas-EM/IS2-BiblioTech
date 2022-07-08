package model.dao;

import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import control.events.Event;
import gui.utils.Pair;
import model.transfers.Transfer;

public abstract class SimulatedObject {
	public String Name;
	protected String id;
	//https://stackoverflow.com/questions/35955204/abstract-classes-inheriting-abstract-methods-with-differing-parameter-type
	protected abstract Pair<Event, String> alta(Transfer t);
	protected abstract Pair<Event, String> baja(String id);
	protected abstract Pair<Event, String> modificar(Transfer t);
	protected abstract Transfer mostrar(Transfer t);
	protected abstract Transfer get(String id);
	protected abstract List<String> getAllID();
	protected abstract Pair<Event, String> reservar(String usuario, String id);
	protected abstract Pair<Event, String> cancelar_reserva(String usuario, String id);
	
	public Field[] getAttributes(){
		return this.getClass().getDeclaredFields();
	}
	
	// Returns list of JButtons required by that class with their listeners already added
	public abstract List<JButton> getButtonList();	
	// Returns list of Events that will be associated with Buttons
	public abstract List<Event> getButtonEventList();
	
	//alta
	public abstract List<JLabel> getLabelListAlta(int width);
	public abstract List<JTextField> getTextFieldListAlta(int width);//this might have to change to List<somethingmoregeneric> in case entity uses radio buttons for example instead of textfields
	public abstract ActionListener getActionListenerAlta(List<JTextField> textFields);
	
	//modificar/delete step 1 (search entity)
	public abstract JLabel getLabelSearch(int width);
	public abstract JTextField getTextFieldSearch(int width);
	public abstract ActionListener getActionListenerSearch(JTextField textField, Event evnt);
	
	//modificar step 2 (we can use same label/textfield lists from Alta for modify step 2)
	public abstract ActionListener getActionListenerModificar(List<JTextField> textFields, int id);
	public abstract Event getEventBaja();
	public abstract Event getEventReservar();
	public abstract Event getEventCancelarReserva();
	//reservar / cancelar_reservar
	public abstract List<JLabel> getLabelListReserva(int width);
	public abstract List<JTextField> getTextFieldListReserva(int width);
	public abstract ActionListener getActionListenerReserva(List<JTextField> textFields, Event e, int id);
	
	public String getName() {
		return this.Name;
	}
}
