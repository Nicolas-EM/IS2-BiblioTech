package control.commands.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.commands.Command;
import control.events.Event;
import gui.utils.ComponentsBuilder;
import gui.utils.CustomSwingUtils;
import model.dao.UsuarioDAO;
import model.services.GUIService;
import model.transfers.TransferUsuario;

public class ShowUsuario extends Command {
	private static Event id = Event.MOSTRAR_USUARIO;

	public ShowUsuario() {
		super(id);
	}

	@Override
	public boolean execute(Object o) {
		String dni;
		boolean cierto = false;
		List<String> dniList = UsuarioDAO.getInstance().getAllDNI();
		dni = o.toString();
		TransferUsuario tUsuario = UsuarioDAO.getInstance().getUsuario(dni);
		
		if(tUsuario != null) {
			GUIService.getInstance().showMessage(createRightPanel(tUsuario));
		}
		else {
			dni = GUIService.getInstance().getInput("Enter DNI");
			for(int i = 0; i< dniList.size() &&  cierto == false ; i++) {
				if(dniList.get(i).equals(dni))
					cierto = true;
			}
			if(cierto == true)
			{tUsuario = UsuarioDAO.getInstance().getUsuario(dni);
			GUIService.getInstance().showMessage(createRightPanel(tUsuario));
			}
			else
				GUIService.getInstance().showError("Invalid field(s)");
		}
		
		return false;
	}

	private JPanel createRightPanel(TransferUsuario tUsuario) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        CustomSwingUtils.changeComponentSize(panel, new Dimension(350, 290));
		
		// Title
		JLabel title = new JLabel("User Details", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		panel.add(title);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		// User Info
		JPanel userInfoPanel = createUserInfoJPanel(tUsuario);
		panel.add(userInfoPanel);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        return panel;
	}
	
	private JPanel createUserInfoJPanel(TransferUsuario tUsuario) {
		JPanel userInfoPanel = new JPanel();
		userInfoPanel.setLayout(null);
		
		JLabel dniLabel = ComponentsBuilder.createLabel("DNI", 35, 0, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
		JTextField dniField = ComponentsBuilder.createTextField(160, 12, 150, 25);
		dniField.setFont(new Font("Arial",Font.PLAIN, 16));
		dniField.setEditable(false);
		dniField.setText(tUsuario.getDni());
		userInfoPanel.add(dniLabel);
		userInfoPanel.add(dniField);

		JLabel fNameLabel = ComponentsBuilder.createLabel("First Name", 35, 30, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
		JTextField fNameField = ComponentsBuilder.createTextField(160, 42, 150, 25);
		fNameField.setFont(new Font("Arial",Font.PLAIN, 16));
		fNameField.setEditable(false);
		fNameField.setText(tUsuario.getName());
		userInfoPanel.add(fNameLabel);
		userInfoPanel.add(fNameField);

		JLabel lNameLabel = ComponentsBuilder.createLabel("Last Name", 35, 60, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
		JTextField lNameField = ComponentsBuilder.createTextField(160, 72, 150, 25);
		lNameField.setFont(new Font("Arial",Font.PLAIN, 16));
		lNameField.setEditable(false);
		lNameField.setText(tUsuario.getSurname());
		userInfoPanel.add(lNameLabel);
		userInfoPanel.add(lNameField);
		
		JLabel emailLabel = ComponentsBuilder.createLabel("Email", 35, 90, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
		JTextField emailField = ComponentsBuilder.createTextField(160, 102, 150, 25);
		emailField.setFont(new Font("Arial",Font.PLAIN, 16));
		emailField.setEditable(false);
		emailField.setText(tUsuario.getCorreo());
		userInfoPanel.add(emailLabel);
		userInfoPanel.add(emailField);
		
		JLabel dobLabel = ComponentsBuilder.createLabel("DOB", 35, 120, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
		JTextField dobField = ComponentsBuilder.createTextField(160, 132, 150, 25);
		dobField.setFont(new Font("Arial",Font.PLAIN, 16));
		dobField.setEditable(false);
		dobField.setText(tUsuario.getFnacimiento().toString());
		userInfoPanel.add(dobLabel);
		userInfoPanel.add(dobField);
		
		JLabel permissionsLabel = ComponentsBuilder.createLabel("Permissions", 35, 150, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
		JTextField permissionsField = ComponentsBuilder.createTextField(160, 162, 150, 25);
		permissionsField.setFont(new Font("Arial",Font.PLAIN, 16));
		permissionsField.setEditable(false);
		permissionsField.setText(tUsuario.getPermissions().toString());
		userInfoPanel.add(permissionsLabel);
		userInfoPanel.add(permissionsField);
		
		return userInfoPanel;
	}
}
