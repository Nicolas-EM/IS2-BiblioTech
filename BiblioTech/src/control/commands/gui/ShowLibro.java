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
import model.services.GUIService;
import model.transfers.TransferLibro;
import model.dao.items.Libro;

public class ShowLibro extends Command {

	private static Event id = Event.MOSTRAR_LIBRO;
	public ShowLibro() {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Object o) {
		String id =  o.toString();
		Libro l = new Libro();
		boolean cierto = false;
		List<String> idList  = l.getAllID();
		TransferLibro tl = l.get(id) ;
		if(tl!= null)
			GUIService.getInstance().showMessage(createRightPanel(tl));
		else {
			id = GUIService.getInstance().getInput("Enter Titulo");
			for(int i = 0; i< idList.size() &&  cierto == false ; i++) {
				if(idList.get(i).equals(id))
					cierto = true;
			}
			if(cierto == true)
			{tl = l.get(id) ;
			GUIService.getInstance().showMessage(createRightPanel(tl));
			}
			else
				GUIService.getInstance().showError("Este Libro No Existe");
		}
		
		return false;
	}

	private JPanel createRightPanel(TransferLibro tl) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        CustomSwingUtils.changeComponentSize(panel, new Dimension(350, 290));
		
		JLabel title = new JLabel("Libro Details", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		panel.add(title);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		// User Info
		JPanel libroInfoPanel = createLibrosInfoJPanel(tl);
		panel.add(libroInfoPanel);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        return panel;
	}

	private JPanel createLibrosInfoJPanel(TransferLibro tl) {
		JPanel libroInfoPanel = new JPanel();
		libroInfoPanel.setLayout(null);
		
		JLabel isbnLabel = ComponentsBuilder.createLabel("ISBN", 35, 0, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
		JTextField isbnField = ComponentsBuilder.createTextField(160, 12, 150, 25);
		isbnField.setFont(new Font("Arial",Font.PLAIN, 16));
		isbnField.setEditable(false);
		isbnField.setText(String.valueOf(tl.getISBN()));
		libroInfoPanel.add(isbnLabel);
		libroInfoPanel.add(isbnField);
		
		JLabel NameLabel = ComponentsBuilder.createLabel("Titulo", 35, 30, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
		JTextField NameField = ComponentsBuilder.createTextField(160, 42, 150, 25);
		NameField.setFont(new Font("Arial",Font.PLAIN, 16));
		NameField.setEditable(false);
		NameField.setText(tl.getTitulo());
		libroInfoPanel.add(NameLabel);
		libroInfoPanel.add(NameField);
		
		JLabel disponibleLabel ;
		JTextField disponibleField ;
		
		if (tl.getCantidad() > 0) {
			disponibleLabel = ComponentsBuilder.createLabel("Disponibles", 35, 60, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
			disponibleField = ComponentsBuilder.createTextField(160, 72, 150, 25);
			disponibleField.setFont(new Font("Arial",Font.PLAIN, 16));
			disponibleField.setEditable(false);
			disponibleField.setText(String.valueOf(tl.getCantidad()));
			libroInfoPanel.add(disponibleField);
			}
		else {
			disponibleLabel = ComponentsBuilder.createLabel("No Disponible", 35, 60, 150, 50, Color.BLACK, new Font("Arial", Font.BOLD, 20));
			disponibleField = ComponentsBuilder.createTextField(160, 72, 150, 25);
		}
		libroInfoPanel.add(disponibleLabel);
		
		return libroInfoPanel;
	}

}
