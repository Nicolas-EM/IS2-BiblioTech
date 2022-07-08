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

public class JPanelAlta extends JPanelCreator {
	private static final int width = 600;
	private static final int height = 275;
	private static final String GUIName = "GUIAlta";
	
	private JPanel mainPanel;
	
	public JPanelAlta(SimulatedObject _o) {
		super(GUIName, width, height, _o);
	}
	
	@Override
	protected JPanel getJPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		// Title
		JLabel titleLabel = ComponentsBuilder.createSimpleLabel("Register " + o.Name, Color.BLACK, new Font("Arial", Font.BOLD, 30));
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
		List<JLabel> labels = o.getLabelListAlta(width);
		List<JTextField> textFields = o.getTextFieldListAlta(width);
		// Left labels
		for(int i = 0; i < labels.size(); i++) {//label and textfield lists must have same size!!!!!!!!!!!
			topPanel.add(labels.get(i));
			topPanel.add(textFields.get(i));
		}

		// Buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(null);
		buttonsPanel.setPreferredSize(new Dimension(800, 50));
		
		JButton buttonCancel = ComponentsBuilder.createButton("Cancel", 125, 0, 100, 30, new Font("Serif", Font.PLAIN, 20));
		buttonsPanel.add(buttonCancel);
		ActionListener lCancel = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearFields(textFields);
				Controller.getInstance().action(Event.SHOW_MAINSCREEN, null);
			}
		};
		buttonCancel.addActionListener(lCancel);
		GUIService.getInstance().setCloseAction(Event.SHOW_MAINSCREEN);//close pressing window X
		
		JButton buttonConfirm = ComponentsBuilder.createButton("Confirm", 375, 0, 100, 30, new Font("Serif", Font.PLAIN, 20));
		buttonsPanel.add(buttonConfirm);
		ActionListener lConfirm = o.getActionListenerAlta(textFields);
		buttonConfirm.addActionListener(lConfirm);		
		
		GUIService.getInstance().setDefaultButton(buttonConfirm);
		
		mainPanel.add(topPanel, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		
		return mainPanel;
	}
	
	private void clearFields(List<JTextField> textFields)
	{
		for(int i = 0; i < textFields.size(); i++)
			textFields.get(i).setText("");
	}
}
