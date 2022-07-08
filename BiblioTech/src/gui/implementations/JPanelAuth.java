package gui.implementations;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import control.controller.Controller;
import control.events.Event;
import gui.JPanelCreator;
import gui.utils.ComponentsBuilder;
import gui.utils.JTextFieldCharLimit;
import model.services.GUIService;
import model.transfers.TransferUsuario;

public class JPanelAuth extends JPanelCreator {
	private static final int width = 330;
	private static final int height = 300;
	private static final String GUIName = "GUIAuth";

	public JPanelAuth() {
		super(GUIName, width, height);
	}
	
	@Override
	protected JPanel getJPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JLabel labelAuth = ComponentsBuilder.createLabel("BiblioTech Login", 45, 20, 223, 50, Color.BLACK, new Font("Arial", Font.PLAIN, 30));
		panel.add(labelAuth);
		
		JLabel labelUsername = ComponentsBuilder.createLabel("DNI", 18, 100, 70, 20, Color.BLACK, new Font("Arial", Font.PLAIN, 14));
		
		panel.add(labelUsername);
		
		JTextField fieldUsername = ComponentsBuilder.createTextField(105, 100, 175, 20);
		fieldUsername.setDocument(new JTextFieldCharLimit(9));
		panel.add(fieldUsername);
		
		JLabel labelPwd = ComponentsBuilder.createLabel("Password", 18, 125, 70, 20, Color.BLACK, new Font("Arial", Font.PLAIN, 14));
		panel.add(labelPwd);
		
		JPasswordField fieldPwd = ComponentsBuilder.createPasswordField(105, 125, 175, 20);
		panel.add(fieldPwd);
		
		// Login button
		JButton buttonLogin = ComponentsBuilder.createButton("Login", 40, 200, 100, 30, new Font("Serif", Font.PLAIN, 14));
		panel.add(buttonLogin);
		
		ActionListener lLogin = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!fieldUsername.getText().equals("") && !(String.valueOf(fieldPwd.getPassword()).equals(""))){
					TransferUsuario usuario = new TransferUsuario(fieldUsername.getText(), String.valueOf(fieldPwd.getPassword()));
					
					fieldUsername.setText("");
					fieldPwd.setText("");
					
					Controller.getInstance().action(Event.AUTH_USER, usuario);
				}
				else {
					Controller.getInstance().action(Event.ERROR, "Username and password can not be empty");
				}
			}
		};
		
		buttonLogin.addActionListener(lLogin);
		
		GUIService.getInstance().setDefaultButton(buttonLogin);
		
		// Register button
		JButton buttonRegister = ComponentsBuilder.createButton("Register", 180, 200, 100, 30, new Font("Serif", Font.PLAIN, 14));
		panel.add(buttonRegister);
		
		ActionListener lRegister = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().action(Event.SHOW_ALTA_USUARIO, null);
			}
		};
		
		buttonRegister.addActionListener(lRegister);
		
		
		return panel;
	}
}
