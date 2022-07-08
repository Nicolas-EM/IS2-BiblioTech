package gui.implementations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.UserPermissions;
import control.controller.Controller;
import control.events.Event;
import gui.GUI;
import gui.utils.ComponentsBuilder;
import gui.utils.JTextFieldCharLimit;
import model.dao.Usuario;
import model.transfers.TransferLibro;
import model.utils.FieldValidator;

public class GUIShowError extends GUI {

private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	JLabel titleLabel;
	public GUIShowError() {
		super("");
		
		this.width = 600;
		this.height = 400;
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBounds(GUI.screenWidth / 2 - width / 2, GUI.screenHeight / 2 - height / 2, width, height);
		
		this.setResizable(false);
		this.setTitle("BiblioTech");
		
		// Title
		titleLabel = ComponentsBuilder.createSimpleLabel("ERROR", Color.BLACK, new Font("Arial", Font.BOLD, 30));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(titleLabel, BorderLayout.NORTH);
		
		mainPanel = createMainPanel();
		this.add(mainPanel);
		
		this.setVisible(true);
	}
	
	public void setError(String error) {
		titleLabel.setText(error);
	}
	
	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		JPanel topPanel = new JPanel(new GridLayout(1,2));
		
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(null);
		rightPanel.setLayout(null);

		
		topPanel.add(leftPanel);
		topPanel.add(rightPanel);
		
		
		// Buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(null);
		buttonsPanel.setPreferredSize(new Dimension(800, 50));
		
		JButton buttonCancel = ComponentsBuilder.createButton("OK", 125, 0, 100, 30, new Font("Serif", Font.PLAIN, 20));
		buttonsPanel.add(buttonCancel);
		ActionListener lCancel = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIShowError.this.dispose();
			}
		};
		buttonCancel.addActionListener(lCancel);
		
		mainPanel.add(topPanel, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		return mainPanel;
	}
}
