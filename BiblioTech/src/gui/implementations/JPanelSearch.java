package gui.implementations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class JPanelSearch extends JPanelCreator {//here user inputs entity id to search in db
	private static final int width = 600;
	private static final int height = 275;
	private static final String GUIName = "GUISearch";
	private JPanel mainPanel;
	private String GUINamePlusEntity;
	
	public JPanelSearch(SimulatedObject _o, Event e) {
		super(GUIName, width, height, _o, e);
		updateActionListenerSearchEvent(e);
	}
	@Override
	protected JPanel getJPanel() {
		GUINamePlusEntity = GUIName + o.Name;
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		// Title
		JLabel titleLabel = ComponentsBuilder.createSimpleLabel("Search " + o.Name, Color.BLACK, new Font("Arial", Font.BOLD, 30));
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
		JLabel label = o.getLabelSearch(width);
		JTextField textField = o.getTextFieldSearch(width);
		GUIService.getInstance().searchTextFieldMap.put(GUINamePlusEntity, textField);
		// Left labels
		topPanel.add(label);
		topPanel.add(textField);
		
		// Buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(null);
		buttonsPanel.setPreferredSize(new Dimension(800, 50));
		
		JButton buttonCancel = ComponentsBuilder.createButton("Cancel", 125, 0, 100, 30, new Font("Serif", Font.PLAIN, 20));
		buttonsPanel.add(buttonCancel);
		ActionListener lCancel = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					textField.setText("");
					Controller.getInstance().action(Event.SHOW_MAINSCREEN, null);
			}
		};
		buttonCancel.addActionListener(lCancel);
		GUIService.getInstance().setCloseAction(Event.SHOW_MAINSCREEN);//close pressing window X
		JButton buttonConfirm = ComponentsBuilder.createButton("Confirm", 375, 0, 100, 30, new Font("Serif", Font.PLAIN, 20));
		buttonsPanel.add(buttonConfirm);
		ActionListener lConfirm = o.getActionListenerSearch(textField, searchEvent);
		buttonConfirm.addActionListener(lConfirm);
		GUIService.getInstance().searchConfirmButtonMap.put(GUINamePlusEntity, buttonConfirm);//save to update the action listener later
		// TODO: ver si esto se puede arreglar (usar GUIService)
//		mainPanel.setDefaultButton(buttonConfirm);
		
		mainPanel.add(topPanel, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		return mainPanel;
	}
	
	public void updateActionListenerSearchEvent(Event e) {
		if(GUINamePlusEntity == null)
			GUINamePlusEntity = GUIName + o.Name;
		JTextField textField = GUIService.getInstance().searchTextFieldMap.get(GUINamePlusEntity);
		JButton confirmBtn = GUIService.getInstance().searchConfirmButtonMap.get(GUINamePlusEntity);
		ActionListener actionListeners[] = confirmBtn.getActionListeners();
		for(ActionListener a : actionListeners) {
			confirmBtn.removeActionListener(a);
		}
		confirmBtn.addActionListener(o.getActionListenerSearch(textField, e));
	}
}
