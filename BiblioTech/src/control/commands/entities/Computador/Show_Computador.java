package control.commands.entities.Computador;

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
import model.dao.items.Computador;
import model.services.GUIService;
import model.transfers.TransferComputador;

public class Show_Computador extends Command {

	private static Event id = Event.MOSTRAR_PC;
	public Show_Computador() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Object o) {
		String id =  o.toString();
		boolean cierto = false;
		Computador c= new Computador();
		List<String> idList  = c.getAllID();
		TransferComputador tc = c.get(id) ;
		if(tc!= null)
			GUIService.getInstance().showMessage(createRightPanel(tc));
		else {
			id = GUIService.getInstance().getInput("Enter ID");
			for(int i = 0; i< idList.size() &&  cierto == false ; i++) {
				if(idList.get(i).equals(id))
					cierto = true;
			}
			if(cierto == true)
			{
				tc = c.get(id) ;
				GUIService.getInstance().showMessage(createRightPanel(tc));
			}
			else
				GUIService.getInstance().showError("Invalid field(s)");
		}
		
		return false;
	}

	private Object createRightPanel(TransferComputador tc) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        CustomSwingUtils.changeComponentSize(panel, new Dimension(350, 290));
		
		JLabel title = new JLabel("PC Details", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		panel.add(title);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		// User Info
		JPanel computadorInfoPanel = createComputadoresInfoJPanel(tc);
		panel.add(computadorInfoPanel);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        return panel;
	}

	private JPanel createComputadoresInfoJPanel(TransferComputador tc) {
		JPanel computadorInfoPanel = new JPanel();
		computadorInfoPanel.setLayout(null);
		
		JLabel modeloLabel = ComponentsBuilder.createLabel("MODELO", 35, 0, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
		JTextField modeloField = ComponentsBuilder.createTextField(160, 12, 150, 25);
		modeloField.setFont(new Font("Arial",Font.PLAIN, 16));
		modeloField.setEditable(false);
		modeloField.setText(tc.getModelo());
		computadorInfoPanel.add(modeloLabel);
		computadorInfoPanel.add(modeloField);
		
		JLabel marcaLabel = ComponentsBuilder.createLabel("MARCA", 35, 30, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
		JTextField marcaField = ComponentsBuilder.createTextField(160, 42, 150, 25);
		marcaField.setFont(new Font("Arial",Font.PLAIN, 16));
		marcaField.setEditable(false);
		marcaField.setText(tc.getMarca());
		computadorInfoPanel.add(marcaLabel);
		computadorInfoPanel.add(marcaField);
		
		JLabel disponibleLabel ;
		JTextField disponibleField ;
		
		if (tc.getCantidad() > 0) {
			disponibleLabel = ComponentsBuilder.createLabel("Disponible", 35, 60, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
			disponibleField = ComponentsBuilder.createTextField(160, 72, 150, 25);
			disponibleField.setFont(new Font("Arial",Font.PLAIN, 16));
			disponibleField.setEditable(false);
			disponibleField.setText(String.valueOf(tc.getCantidad()));
			computadorInfoPanel.add(disponibleField);
			}
		else {
			disponibleLabel = ComponentsBuilder.createLabel("No Disponible", 35, 60, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
			disponibleField = ComponentsBuilder.createTextField(160, 72, 150, 25);
		}
		computadorInfoPanel.add(disponibleLabel);
		
		return computadorInfoPanel;
	}

}
