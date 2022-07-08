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

import control.UserPermissions;
import control.controller.Controller;
import control.events.Event;
import gui.JPanelCreator;
import gui.utils.ComponentsBuilder;
import gui.utils.JTextFieldCharLimit;
import model.dao.UsuarioDAO;
import model.services.GUIService;
import model.transfers.TransferUsuario;

public class JPanelUsuarioModificar extends JPanelCreator {
	private static final int width = 600;
	private static final int height = 275;
	private static final String GUIName = "GUIModUsuario";
	private JPanel mainPanel;
	
	public JPanelUsuarioModificar() {
		super(GUIName, width, height);
	}

	@Override
	protected JPanel getJPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		// Title
		JLabel titleLabel = ComponentsBuilder.createSimpleLabel("MODIFICAR USUARIO", Color.BLACK, new Font("Arial", Font.BOLD, 30));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titleLabel, BorderLayout.NORTH);
				
		mainPanel = createMainPanel();
		panel.add(mainPanel);
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		TransferUsuario usuario = UsuarioDAO.getInstance().getUsuario(UsuarioDAO.getInstance().getDni());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		
		// Left labels	
		JLabel fNameLabel = ComponentsBuilder.createLabel("First Name", width/2 - 100, 25, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14));
		JTextField fNameField = ComponentsBuilder.createTextField("", width/2, 40, 150, 20);
		fNameField.setDocument(new JTextFieldCharLimit(32));
		fNameField.setText(usuario.getName());
		topPanel.add(fNameLabel);
		topPanel.add(fNameField);

		JLabel lNameLabel = ComponentsBuilder.createLabel("Last Name", width/2 - 100, 50, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14));
		JTextField lNameField = ComponentsBuilder.createTextField("", width/2, 65, 150, 20);
		lNameField.setDocument(new JTextFieldCharLimit(32));
		lNameField.setText(usuario.getSurname());
		topPanel.add(lNameLabel);
		topPanel.add(lNameField);
		
		JLabel emailLabel = ComponentsBuilder.createLabel("Email", width/2 - 100, 75, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14));
		JTextField emailField = ComponentsBuilder.createTextField(usuario.getCorreo(), width/2, 90, 150, 20);
		emailField.setDocument(new JTextFieldCharLimit(64));
		emailField.setText(usuario.getCorreo());
		topPanel.add(emailLabel);
		topPanel.add(emailField);	
		
		// Buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(null);
		buttonsPanel.setPreferredSize(new Dimension(800, 50));
		
		JButton buttonCancel = ComponentsBuilder.createButton("Cancel", 125, 0, 100, 30, new Font("Serif", Font.PLAIN, 20));
		buttonsPanel.add(buttonCancel);
		ActionListener lCancel = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().action(Event.UPDATE_GUI_MRP_USER, UsuarioDAO.getInstance());
			}
		};
		buttonCancel.addActionListener(lCancel);
		
		JButton buttonConfirm = ComponentsBuilder.createButton("Confirm", 375, 0, 100, 30, new Font("Serif", Font.PLAIN, 20));
		buttonsPanel.add(buttonConfirm);
		ActionListener lConfirm = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fNameField.getText().equals("") || lNameField.getText().equals("") || emailField.getText().equals(""))
					Controller.getInstance().action(Event.ERROR, "All fields are mandatory");
				else {
					Controller.getInstance().action(Event.MODIFICAR_USUARIO, new TransferUsuario(usuario.getDni(), String.valueOf(usuario.getPwd()), fNameField.getText().trim(), lNameField.getText().trim(), usuario.getFnacimiento(), emailField.getText().trim()));
					if(UsuarioDAO.getInstance().getPermissions() == UserPermissions.USER)
						Controller.getInstance().action(Event.UPDATE_GUI_MRP_USER, UsuarioDAO.getInstance());
						
				}		
			}
		};
		buttonConfirm.addActionListener(lConfirm);
		
		GUIService.getInstance().setDefaultButton(buttonConfirm);
		
		mainPanel.add(topPanel, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		return mainPanel;
	}
}
