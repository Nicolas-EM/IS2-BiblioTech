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

import gui.JPanelCreator;
import gui.utils.ComponentsBuilder;
import gui.utils.JTextFieldCharLimit;

public class JPanelGetDNI extends JPanelCreator {
	private static final int width = 300;
	private static final int height = 150;
	private static final String GUIName = "GUIGetDNI";
	private String dni;
	
	public JPanelGetDNI() {
		super(GUIName, width, height);
	}

	@Override
	protected JPanel getJPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		// Title
		JLabel titleLabel = ComponentsBuilder.createSimpleLabel("Insert DNI", Color.BLACK, new Font("Arial", Font.BOLD, 24));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titleLabel, BorderLayout.NORTH);
						
		JPanel mainPanel = createMainPanel();
		panel.add(mainPanel);
		
		return panel;
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(null);

		JTextField dniField = ComponentsBuilder.createTextField(75, 5, 150, 25);
		dniField.setDocument(new JTextFieldCharLimit(9));
		centerPanel.add(dniField);
		
		// Buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(null);
		buttonsPanel.setPreferredSize(new Dimension(800, 50));
		
		JButton buttonCancel = ComponentsBuilder.createButton("Cancel", 30, 5, 100, 30, new Font("Serif", Font.PLAIN, 20));
		buttonsPanel.add(buttonCancel);
		ActionListener lCancel = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: fix this
			}
		};
		buttonCancel.addActionListener(lCancel);
		
		JButton buttonConfirm = ComponentsBuilder.createButton("Confirm", 170, 5, 100, 30, new Font("Serif", Font.PLAIN, 20));
		buttonsPanel.add(buttonConfirm);
		ActionListener lConfirm = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dni = dniField.getText();
			}
		};
		buttonConfirm.addActionListener(lConfirm);
		
		// TODO: fix this
//		this.getRootPane().setDefaultButton(buttonConfirm);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		return mainPanel;
	}
	
	public String getDNI() {
		return dni;
	}
}
