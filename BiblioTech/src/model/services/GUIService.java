package model.services;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.events.Event;

public abstract class GUIService {
	private static GUIService instance;
	protected static JFrame mainWindow;
	protected static WindowAdapter windowEvent;
	protected List<String> GUINames;
	public Map<String, List<JTextField>> modifyTextFieldsMap;
	public Map<String, JButton> modifyConfirmButtonMap;
	public Map<String, JTextField> searchTextFieldMap;
	public Map<String, JButton> searchConfirmButtonMap;
	public Map<String, List<JTextField>> reservaTextFieldsMap;
	public Map<String, JButton> reservaConfirmButtonMap;
	protected JPanel cardsPanel;
	
	public static GUIService getInstance() {
		if (instance == null) instance = new GUIServiceImp();
		return instance;
	}
	
	public void center(int width, int height) {
		Dimension screenInfo = Toolkit.getDefaultToolkit().getScreenSize();
		GUIService.mainWindow.setBounds(screenInfo.width/2 - width/2, screenInfo.height/2 - height/2, width, height);
	}
	
	public abstract void setJPanel(String cardName, int width, int height);
	public abstract void setJPanel(String cardName, int width, int height, JPanel panel);
	public abstract void removeJPanel(JPanel panel);
	public abstract void showWarning(String warning);
	public abstract void showMessage(Object msg);
	public abstract void showError(String error);
	public abstract String getInput(String message);
	public abstract boolean containsJPanel(String cardName);
	public abstract void setDefaultButton(JButton button);
	public abstract void setCloseAction(Event e);
	
	public void exit() {
		GUIService.mainWindow.setVisible(false);
		instance = null;
	}
}
