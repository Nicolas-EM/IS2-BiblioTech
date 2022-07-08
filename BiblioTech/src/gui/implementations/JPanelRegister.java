package gui.implementations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import control.UserPermissions;
import control.controller.Controller;
import control.events.Event;
import gui.JPanelCreator;
import gui.utils.ComponentsBuilder;
import gui.utils.JTextFieldCharLimit;
import model.dao.UsuarioDAO;
import model.services.GUIService;
import model.transfers.TransferUsuario;

public class JPanelRegister extends JPanelCreator {
	private JPanel mainPanel;
	private static final int width = 600;
	private static final int height = 400;
	private static final String GUIName = "GUIRegister";
	
	private JTextField dniField, fNameField, lNameField, emailField;
	private JPasswordField pwdField;

	public JPanelRegister() {
		super(GUIName, width, height);
	}

	@Override
	protected JPanel getJPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		// Title
		JLabel titleLabel = ComponentsBuilder.createSimpleLabel("Register", Color.BLACK, new Font("Arial", Font.BOLD, 30));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titleLabel, BorderLayout.NORTH);

		mainPanel = createMainPanel();
		panel.add(mainPanel);
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		JPanel topPanel = new JPanel(new GridLayout(1,2));
		
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(null);
		rightPanel.setLayout(null);
		
		// Left labels
		JLabel dniLabel = ComponentsBuilder.createLabel("DNI", 20, 0, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14));
		dniField = ComponentsBuilder.createTextField(135, 15, 150, 20);
		dniField.setDocument(new JTextFieldCharLimit(9));
		leftPanel.add(dniLabel);
		leftPanel.add(dniField);

		JLabel fNameLabel = ComponentsBuilder.createLabel("First Name", 20, 25, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14));
		fNameField = ComponentsBuilder.createTextField(135, 40, 150, 20);
		fNameField.setDocument(new JTextFieldCharLimit(32));
		leftPanel.add(fNameLabel);
		leftPanel.add(fNameField);

		JLabel lNameLabel = ComponentsBuilder.createLabel("Last Name", 20, 50, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14));
		lNameField = ComponentsBuilder.createTextField(135, 65, 150, 20);
		lNameField.setDocument(new JTextFieldCharLimit(32));
		leftPanel.add(lNameLabel);
		leftPanel.add(lNameField);
		
		JLabel emailLabel = ComponentsBuilder.createLabel("Email", 20, 75, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14));
		emailField = ComponentsBuilder.createTextField(135, 90, 150, 20);
		emailField.setDocument(new JTextFieldCharLimit(64));
		leftPanel.add(emailLabel);
		leftPanel.add(emailField);
		
		JLabel pwdLabel = ComponentsBuilder.createLabel("Password", 20, 100, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14));
		pwdField = ComponentsBuilder.createPasswordField(135, 115, 150, 20);
		pwdField.setDocument(new JTextFieldCharLimit(64));
		leftPanel.add(pwdLabel);
		leftPanel.add(pwdField);
		
		JLabel dobLabel = ComponentsBuilder.createLabel("Date of Birth", 20, 0, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14));
		JCalendar cal = new JCalendar();
		cal.setBounds(20, 40, 220, 220);
		cal.setMaxSelectableDate(new java.util.Date());
		rightPanel.add(dobLabel);
		rightPanel.add(cal);
		
		topPanel.add(leftPanel);
		topPanel.add(rightPanel);
		
		
		// Buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(null);
		buttonsPanel.setPreferredSize(new Dimension(800, 50));
		
		JButton buttonCancel = ComponentsBuilder.createButton("Cancel", 125, 0, 100, 30, new Font("Serif", Font.PLAIN, 20));
		buttonsPanel.add(buttonCancel);
		ActionListener lCancel = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearFields();
				if(UsuarioDAO.getInstance().getPermissions() == UserPermissions.USER)
					Controller.getInstance().action(Event.SHOW_AUTHENTICATION, null);
			}
		};
		buttonCancel.addActionListener(lCancel);
		
		JButton buttonConfirm = ComponentsBuilder.createButton("Confirm", 375, 0, 100, 30, new Font("Serif", Font.PLAIN, 20));
		buttonsPanel.add(buttonConfirm);
		ActionListener lConfirm = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dniField.getText().equals("") || fNameField.getText().equals("") || lNameField.getText().equals("") || emailField.getText().equals("") || (String.valueOf(pwdField.getPassword()).equals("")))
					Controller.getInstance().action(Event.ERROR, "All fields are mandatory");
				else {
					TransferUsuario user = new TransferUsuario(dniField.getText(), String.valueOf(pwdField.getPassword()), fNameField.getText().trim(), lNameField.getText().trim(), new java.sql.Date(cal.getDate().getTime()), emailField.getText().trim());						
					clearFields();
					Controller.getInstance().action(Event.ALTA_USUARIO, user);
				}
			}
		};
		buttonConfirm.addActionListener(lConfirm);
		
		GUIService.getInstance().setDefaultButton(buttonConfirm);
		
		mainPanel.add(topPanel, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		return mainPanel;
	}
	
	private void clearFields() {
		dniField.setText("");
		fNameField.setText("");
		lNameField.setText("");
		emailField.setText("");
		pwdField.setText("");
	}
}
