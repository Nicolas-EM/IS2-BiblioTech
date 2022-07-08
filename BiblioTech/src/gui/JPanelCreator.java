package gui;

import javax.swing.JPanel;

import control.events.Event;
import model.dao.SimulatedObject;
import model.services.GUIService;

public abstract class JPanelCreator {
	protected SimulatedObject o;
	protected int modifyingEntityId;
	protected Event searchEvent;
	protected JPanelCreator() {}
	
	
	protected JPanelCreator(String GUIName, int width, int height, SimulatedObject _o, Event evnt, int _entityId) {//search
		o = _o;
		searchEvent = evnt;
		modifyingEntityId = _entityId;
		String GUINamePlusEntity = GUIName + _o.Name;
		if(GUIService.getInstance().containsJPanel(GUINamePlusEntity))
			GUIService.getInstance().setJPanel(GUINamePlusEntity, width, height);
		else
			GUIService.getInstance().setJPanel(GUINamePlusEntity, width, height, getJPanel());
	}
	
	protected JPanelCreator(String GUIName, int width, int height, SimulatedObject _o, Event evnt) {//search
		o = _o;
		searchEvent = evnt;
		String GUINamePlusEntity = GUIName + _o.Name;
		if(GUIService.getInstance().containsJPanel(GUINamePlusEntity))
			GUIService.getInstance().setJPanel(GUINamePlusEntity, width, height);
		else
			GUIService.getInstance().setJPanel(GUINamePlusEntity, width, height, getJPanel());
	}
	
	protected JPanelCreator(String GUIName, int width, int height, SimulatedObject _o, int _entityId) {//modificar
		o = _o;
		modifyingEntityId = _entityId;
		String GUINamePlusEntity = GUIName + _o.Name;
		if(GUIService.getInstance().containsJPanel(GUINamePlusEntity))
			GUIService.getInstance().setJPanel(GUINamePlusEntity, width, height);
		else
			GUIService.getInstance().setJPanel(GUINamePlusEntity, width, height, getJPanel());
	}
	
	protected JPanelCreator(String GUIName, int width, int height, SimulatedObject _o) {//alta
		o = _o;
		String GUINamePlusEntity = GUIName + _o.Name;
		if(GUIService.getInstance().containsJPanel(GUINamePlusEntity))
			GUIService.getInstance().setJPanel(GUINamePlusEntity, width, height);
		else
			GUIService.getInstance().setJPanel(GUINamePlusEntity, width, height, getJPanel());
	}
	
	protected JPanelCreator(String GUIName, int width, int height) {
		if(GUIService.getInstance().containsJPanel(GUIName))
			GUIService.getInstance().setJPanel(GUIName, width, height);
		else
			GUIService.getInstance().setJPanel(GUIName, width, height, getJPanel());
	}
	
	protected abstract JPanel getJPanel();
}
