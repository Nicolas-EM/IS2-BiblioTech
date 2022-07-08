package control.commands.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.commands.Command;
import control.events.Event;
import gui.implementations.JPanelMain;
import gui.utils.CustomSwingUtils;
import model.dao.UsuarioDAO;

public class ShowMainUserPanelCommand extends Command {
	public static final Event id = Event.UPDATE_GUI_MRP_USER;

	public ShowMainUserPanelCommand() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public boolean execute(Object o) {
		new JPanelMain(createRightPanel((UsuarioDAO) o), "Usuario");
		return false;
	}

	private JPanel createRightPanel(UsuarioDAO o) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        CustomSwingUtils.changeComponentSize(panel, new Dimension(800, 700));
		
		// Title
		JLabel entityName;
		if(o != null) {
			String dni = o.getDni();
			entityName = new JLabel(Character.toUpperCase(dni.charAt(0)) + dni.substring(1, dni.length()), JLabel.CENTER);
			entityName.setFont(new Font("Arial", Font.BOLD, 30));
			entityName.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			
			panel.add(Box.createRigidArea(new Dimension(0, 20)));
			panel.add(entityName);
			panel.add(Box.createRigidArea(new Dimension(0, 20)));
			
			// Button list
			JPanel buttonPanel = buildButtonList(o); 
	        panel.add(buttonPanel);
		}
        
        return panel;
	}
	
	private JPanel buildButtonList(UsuarioDAO o) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		List<JButton> buttonList = o.getButtonList();
		List<Event> eventList = o.getButtonEventList();
		
		try {
			for(int i = 0; i < buttonList.size(); i++) {
				JButton btn = buttonList.get(i);
				Event e = eventList.get(i);
				
				CustomSwingUtils.addCtrlEventToButton(btn, e, o);
				CustomSwingUtils.setButtonStyleMainPanel(btn);
				
				panel.add(btn);
				panel.add(Box.createRigidArea(new Dimension(0, 20)));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return panel;
	}
}
