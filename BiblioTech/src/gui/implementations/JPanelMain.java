package gui.implementations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.controller.Controller;
import control.events.Event;
import gui.JPanelCreator;
import gui.utils.CustomSwingUtils;
import model.dao.UsuarioDAO;
import model.dao.items.Computador;
import model.dao.items.Libro;
import model.dao.items.Sala;
import model.services.GUIService;

public class JPanelMain extends JPanelCreator {
	private static final int width = 1200;
	private static final int height = 750;
	private static final String GUIName = "GUIMain";
	private static final String homeScreenTitle = "BiblioTech";
	private static JPanel panel;
	private JPanel rightPanel;
	
	public JPanelMain() {
		super(GUIName, width, height);
	}
	
	public JPanelMain(JPanel rightPanel, String objName) {
		super();
		this.rightPanel = rightPanel;
		
		if(JPanelMain.panel != null)
			GUIService.getInstance().removeJPanel(JPanelMain.panel);
		JPanelMain.panel = getJPanel();
		GUIService.getInstance().setJPanel(GUIName + objName, width, height, JPanelMain.panel);
	}
	
	@Override
	protected JPanel getJPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		if(rightPanel == null)
			rightPanel = rightJPanel();
		
		panel.add(leftJPanel());
		panel.add(rightPanel);
		
		return panel;
	}
	
	private JPanel leftJPanel() {
    	JButton librosBtn, salasBtn, computadorBtn, usuarioBtn, homeBtn, logoutButton;
    	JLabel appTitle;
    	JPanel leftBarPanel;
    	
    	// Create panel
    	leftBarPanel = new JPanel();
    	leftBarPanel.setLayout(new BoxLayout(leftBarPanel, BoxLayout.PAGE_AXIS));
    	leftBarPanel.setBackground(new Color(214, 214, 214));
    	CustomSwingUtils.changeComponentSize(leftBarPanel, new Dimension(350, 730));
		
    	// Add title label
    	leftBarPanel.add(Box.createRigidArea(new Dimension(10, 20)));
		appTitle = new JLabel("BiblioTech", JLabel.CENTER);
		appTitle.setFont(new Font("Arial", Font.BOLD, 30));
		appTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		leftBarPanel.add(appTitle);
		leftBarPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
		// Add home button
		homeBtn = createLeftBarButton(leftBarPanel, "HOME"); 
		ActionListener listenerHome = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().action(Event.SHOW_MAINSCREEN, null);
			}
		};

		homeBtn.addActionListener(listenerHome);
		
		// Add libros button
        librosBtn = createLeftBarButton(leftBarPanel, "LIBROS");
		ActionListener listenerLibros = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Libro());
			}
		};
		librosBtn.addActionListener(listenerLibros);

		
		// Add salas button
        salasBtn = createLeftBarButton(leftBarPanel, "SALAS"); 
		ActionListener listenerSalas = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Sala());
			}
		};
		salasBtn.addActionListener(listenerSalas);
        
		
		// Add computador button
        computadorBtn = createLeftBarButton(leftBarPanel, "COMPUTADORES"); 
		ActionListener listenerComputador = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Computador());
			}
		};
		computadorBtn.addActionListener(listenerComputador);
		
		usuarioBtn = createLeftBarButton(leftBarPanel, "USUARIOS"); 
		ActionListener listenerUsuario = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().action(Event.UPDATE_GUI_MRP_USER, UsuarioDAO.getInstance());
			}
		};
		usuarioBtn.addActionListener(listenerUsuario);
		
		// Add log out button
        logoutButton = createLeftBarButton(leftBarPanel, "LOG OUT"); 
		ActionListener listenerLogOut = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UsuarioDAO.getInstance().logout();
				GUIService.getInstance().exit();
				Controller.getInstance().action(Event.SHOW_AUTHENTICATION, null);
			}
		};
		logoutButton.addActionListener(listenerLogOut);

		return leftBarPanel;
    }
	
	private JButton createLeftBarButton(JPanel leftBarPanel, String name) {
		JButton btn = new JButton(name);
    	CustomSwingUtils.setButtonStyle(btn);
    	leftBarPanel.add(btn);
    	leftBarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    	return btn;
	}
	
	private JPanel rightJPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		// Title
		JPanel titlePanel = new JPanel();
		titlePanel.setPreferredSize(new Dimension(width - 350, 100));
		JLabel entityName = new JLabel(homeScreenTitle, JLabel.CENTER);
		entityName.setFont(new Font("Arial", Font.BOLD, 30));
		entityName.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titlePanel.add(Box.createRigidArea(new Dimension(0, 75)));
		titlePanel.add(entityName);
		
		panel.add(titlePanel, BorderLayout.NORTH);
        
        return panel;
	}

	public void setRightPanel(JPanel rightPanel) {
		JPanel temp = this.getJPanel();
		if(rightPanel != null) temp.remove(this.rightPanel);
		this.rightPanel = rightPanel;
		temp.add(this.rightPanel);
	}
}
