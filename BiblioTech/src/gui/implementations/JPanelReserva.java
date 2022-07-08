package gui.implementations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.controller.Controller;
import control.events.Event;
import gui.JPanelCreator;
import gui.utils.ComponentsBuilder;
import model.dao.SimulatedObject;
import model.services.GUIService;

public class JPanelReserva extends JPanelCreator {
	//This class was used to ask user for a dni with a text input but we now use logged user's dni
	private static final int width = 600;
	private static final int height = 275;
	private static final String GUIName = "GUIReserva";
	private JPanel mainPanel;
	private String GUINamePlusEntity;
	
	public JPanelReserva(SimulatedObject _o, Event evnt, int id) {
		super(GUIName, width, height, _o, evnt, id);
		updateActionListenerReservaEvent(evnt, id);
	}
	
	@Override
	protected JPanel getJPanel() {
		GUINamePlusEntity = GUIName + o.Name;
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		// Title
		JLabel titleLabel = ComponentsBuilder.createSimpleLabel("Reservar " + o.Name, Color.BLACK, new Font("Arial", Font.BOLD, 30));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titleLabel, BorderLayout.NORTH);
		
		mainPanel = createMainPanel();
		panel.add(mainPanel);
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		
		//System.out.println("createMainPanel: " + o.toString());
		List<JLabel> labels = o.getLabelListReserva(width);
		List<JTextField> textFields = o.getTextFieldListReserva(width);
		//textFields = o.getTextFieldListAlta();
		// Left labels
		for(int i = 0; i < labels.size(); i++) {//label and textfield lists must have same size!!!!!!!!!!!
			topPanel.add(labels.get(i));
			topPanel.add(textFields.get(i));
		}
		GUIService.getInstance().reservaTextFieldsMap.put(GUINamePlusEntity, textFields);
		
		// Buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(null);
		buttonsPanel.setPreferredSize(new Dimension(800, 50));
		
		JButton buttonCancel = ComponentsBuilder.createButton("Cancel", 125, 0, 100, 30, new Font("Serif", Font.PLAIN, 20));
		buttonsPanel.add(buttonCancel);
		ActionListener lCancel = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < textFields.size(); i++)
					textFields.get(i).setText("");
					Controller.getInstance().action(Event.SHOW_MAINSCREEN, null);
			}
		};
		buttonCancel.addActionListener(lCancel);
		GUIService.getInstance().setCloseAction(Event.SHOW_MAINSCREEN);//close pressing window X
		JButton buttonConfirm = ComponentsBuilder.createButton("Confirm", 375, 0, 100, 30, new Font("Serif", Font.PLAIN, 20));
		buttonsPanel.add(buttonConfirm);
		ActionListener lConfirm = o.getActionListenerReserva(textFields, searchEvent, modifyingEntityId);
		buttonConfirm.addActionListener(lConfirm);
		GUIService.getInstance().reservaConfirmButtonMap.put(GUINamePlusEntity, buttonConfirm);//save to update the action listener later
		// TODO: ver si esto se puede arreglar (usar GUIService)
//		mainPanel.setDefaultButton(buttonConfirm);
		
		mainPanel.add(topPanel, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		return mainPanel;
	}
	
	public void updateActionListenerReservaEvent(Event e, int id) {
		if(GUINamePlusEntity == null)
			GUINamePlusEntity = GUIName + o.Name;
		List<JTextField> textFields = GUIService.getInstance().reservaTextFieldsMap.get(GUINamePlusEntity);
		JButton confirmBtn = GUIService.getInstance().reservaConfirmButtonMap.get(GUINamePlusEntity);
		ActionListener actionListeners[] = confirmBtn.getActionListeners();
		for(ActionListener a : actionListeners) {
			confirmBtn.removeActionListener(a);
		}
		confirmBtn.addActionListener(o.getActionListenerReserva(textFields, e, id));
	}
}
