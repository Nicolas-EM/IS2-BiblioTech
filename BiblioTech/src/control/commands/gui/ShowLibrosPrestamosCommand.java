package control.commands.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.commands.Command;
import control.controller.Controller;
import control.events.Event;
import gui.implementations.JPanelMain;
import gui.utils.CustomSwingUtils;

public class ShowLibrosPrestamosCommand extends Command {
	private static final Event id = null;
	
	public ShowLibrosPrestamosCommand() {
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		new JPanelMain(createRightPanel(), "LPrestamos");
		return false;
	}

	private JPanel createRightPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        CustomSwingUtils.changeComponentSize(panel, new Dimension(800, 700));
		
		// Title
		JLabel entityName = new JLabel("", JLabel.CENTER);
		entityName.setFont(new Font("Arial", Font.BOLD, 30));
		entityName.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		panel.add(entityName);
		
		// Button list
		JPanel buttonList = buildButtonList(); 
        buttonList.add(Box.createRigidArea(new Dimension(0, 100)));
        panel.add(buttonList);
        
        return panel;
	}

	private JPanel buildButtonList() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton prestarBtn, regresarBtn;
		
		prestarBtn = new JButton("PRESTAR"); 
		ActionListener listenerPrestar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().action(Event.PRESTAR, null);
			}
		};
		prestarBtn.addActionListener(listenerPrestar);
		CustomSwingUtils.setButtonStyleMainPanel(prestarBtn);
		panel.add(prestarBtn);
		panel.add(Box.createRigidArea(new Dimension(20, 0)));
		
		
		regresarBtn = new JButton("REGRESAR"); 
		ActionListener listenerRegresar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().action(Event.REGRESAR, null);
			}
		};
		regresarBtn.addActionListener(listenerRegresar);
		CustomSwingUtils.setButtonStyleMainPanel(regresarBtn);
		panel.add(regresarBtn);
		panel.add(Box.createRigidArea(new Dimension(20, 0)));
		
		return panel;
	}
}