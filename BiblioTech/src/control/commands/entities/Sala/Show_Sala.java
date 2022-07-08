package control.commands.entities.Sala;

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
import model.dao.items.Sala;
import model.services.GUIService;
import model.transfers.TransferSala;

public class Show_Sala  extends Command{

	private static Event id = Event.MOSTRAR_SALA;
	public Show_Sala() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Object o) {
		String id =  o.toString();
		boolean cierto = false;
		Sala s= new Sala();
		List<String> idList  = s.getAllID();
		TransferSala ts = s.get(id) ;
		if(ts!= null)
			GUIService.getInstance().showMessage(createRightPanel(ts));
		else {
			id = GUIService.getInstance().getInput("Enter ID");
			for(int i = 0; i< idList.size() &&  cierto == false ; i++) {
				if(idList.get(i).equals(id))
					cierto = true;
			}
			if(cierto == true)
			{
				ts = s.get(id) ;
				GUIService.getInstance().showMessage(createRightPanel(ts));
			}
			else
				GUIService.getInstance().showError("Invalid field(s)");
		}
		
		return false;
	}

	private Object createRightPanel(TransferSala ts) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        CustomSwingUtils.changeComponentSize(panel, new Dimension(350, 290));
		
		JLabel title = new JLabel("Sala Details", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		panel.add(title);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		// User Info
		JPanel salaInfoPanel = createSalasInfoJPanel(ts);
		panel.add(salaInfoPanel);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        return panel;
	}

	private JPanel createSalasInfoJPanel(TransferSala ts) {
		JPanel salaInfoPanel = new JPanel();
		salaInfoPanel.setLayout(null);
		
		JLabel nameLabel = ComponentsBuilder.createLabel("NOMBRE", 35, 0, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
		JTextField nameField = ComponentsBuilder.createTextField(160, 12, 150, 25);
		nameField.setFont(new Font("Arial",Font.PLAIN, 16));
		nameField.setEditable(false);
		nameField.setText(ts.getNombre());
		salaInfoPanel.add(nameLabel);
		salaInfoPanel.add(nameField);
		
		JLabel disponibleLabel ;
		JTextField disponibleField ;
		
		disponibleLabel = ComponentsBuilder.createLabel("CAPACIDAD", 35, 30, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
		disponibleField = ComponentsBuilder.createTextField(160, 42, 150, 25);
		disponibleField.setFont(new Font("Arial",Font.PLAIN, 16));
		disponibleField.setEditable(false);
		disponibleField.setText(String.valueOf(ts.getCantidad()));
		salaInfoPanel.add(disponibleField);
		salaInfoPanel.add(disponibleLabel);
		
		return salaInfoPanel;
	}
}
