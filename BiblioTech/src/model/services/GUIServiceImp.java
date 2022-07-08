package model.services;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.controller.Controller;
import control.events.Event;

public class GUIServiceImp extends GUIService {
	protected static final ImageIcon iconImg = new ImageIcon("./src/gui/resources/icon.png");
	
	public GUIServiceImp() {
		GUIService.mainWindow = new JFrame();
		GUIService.mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUIService.windowEvent = null;
		GUIService.mainWindow.setIconImage(GUIServiceImp.iconImg.getImage());

		GUIService.mainWindow.setResizable(false);
		GUIService.mainWindow.setTitle("BiblioTech");

		cardsPanel = new JPanel(new CardLayout());
		GUIService.mainWindow.getContentPane().add(cardsPanel);

		GUINames = new ArrayList<>();
		modifyTextFieldsMap = new HashMap<>();
		modifyConfirmButtonMap = new HashMap<>();
		searchTextFieldMap = new HashMap<>();
		searchConfirmButtonMap = new HashMap<>();
		reservaTextFieldsMap = new HashMap<>();
		reservaConfirmButtonMap = new HashMap<>();
	}

	@Override
	public void setJPanel(String cardName, int width, int height, JPanel panel) {
		this.GUINames.add(cardName);
		this.cardsPanel.add(panel, cardName);
		
		setJPanel(cardName, width, height);
	}
	
	@Override
	public void setJPanel(String cardName, int width, int height) {
		CardLayout cardsLayout = (CardLayout)(this.cardsPanel.getLayout());
		cardsLayout.show(cardsPanel, cardName);
		
		this.center(width, height);
		GUIService.mainWindow.setVisible(true);
	}
	
	@Override
	public void removeJPanel(JPanel panel) {
		this.cardsPanel.remove(panel);
	}
	
	@Override
	public void showWarning(String warning) {
		JOptionPane.showMessageDialog(null, warning, "Warning", JOptionPane.WARNING_MESSAGE);
	}
	
	@Override
	public void showMessage(Object msg) {
		JOptionPane.showMessageDialog(null, msg, "Message", JOptionPane.PLAIN_MESSAGE);
	}
	
	@Override
	public void showError(String error) {
		JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public String getInput(String message) {
		return JOptionPane.showInputDialog(message);
	}
	
	@Override
	public boolean containsJPanel(String cardName) {
		return this.GUINames.contains(cardName);
	}
	
	@Override
	public void setDefaultButton(JButton button) {
		mainWindow.getRootPane().setDefaultButton(button);
	}

	@Override
	public void setCloseAction(Event event) {//when clicking the close button on alta window for example it should return to main window
		if(event != null) {
			GUIService.mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			GUIService.windowEvent = new WindowAdapter()
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	            	Controller.getInstance().action(event, null);
	            }
	        };
			GUIService.mainWindow.addWindowListener(GUIService.windowEvent);
		} else { //if event is null
			if(GUIService.windowEvent != null) {
				GUIService.mainWindow.removeWindowListener(GUIService.windowEvent);
				GUIService.mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		}
	}
}
