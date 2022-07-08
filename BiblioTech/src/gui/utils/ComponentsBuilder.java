package gui.utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ComponentsBuilder {
	public static JLabel createLabel(String text, int x, int y, int width, int height, Color color, Font font) {
		JLabel label = createSimpleLabel(text, color, font);

		label.setBounds(x, y, width, height);
		
		return label;
	}
	
	public static JLabel createSimpleLabel(String text, Color color, Font font) {
		JLabel label = new JLabel(text);
		
		label.setForeground(color);
		label.setFont(font);
		
		return label;
	}
	
	public static JButton createButton(String text, int x, int y, int width, int height, Font font) {
		JButton button = createSimpleButton(text, font);
		button.setBounds(x, y, width, height);       
		return button;
	}
	
	public static JButton createSimpleButton(String text, Font font) {
		JButton button = new JButton(text);
		button.setFont(font);
		return button;
	}
	

//	public static JButton createBackButton() {
//		JButton button = new JButton();
//		button.setBounds(40, 25, 60, 60);
//		button.setIcon(new ImageIcon("resources/arrow-left-solid.png"));
//		button.setBorderPainted(false); 
//        button.setContentAreaFilled(false); 
//        button.setFocusPainted(false); 
//        button.setOpaque(false);
//        button.setToolTipText("Atras");
//		return button;
//	}
	
//	public static JButton createBackButtonSmall() {
//		JButton button = new JButton();
//		button.setBounds(20, 32, 30, 30);
//		button.setIcon(new ImageIcon("resources/arrow-left-solid_small.png"));
//		button.setBorderPainted(false); 
//        button.setContentAreaFilled(false); 
//        button.setFocusPainted(false); 
//        button.setOpaque(false);
//        button.setToolTipText("Atras");
//		return button;
//	}
	
//	public static JButton createCarritoButton() {
//		JButton button = new JButton();
//		button.setBounds(900, 25, 60, 60);
//		button.setIcon(new ImageIcon("resources/carrito.png"));
//		button.setBorderPainted(false); 
//        button.setContentAreaFilled(false); 
//        button.setFocusPainted(false); 
//        button.setOpaque(false);
//        button.setToolTipText("Mostrar carrito");
//		return button;
//	}

	public static JTextField createTextField(int x, int y, int width, int height) {
		JTextField textField = new JTextField();
		textField.setBounds(x, y, width, height);
//		Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
//		textField.setCursor(cursor);
		return textField;
	}
	
	public static JTextField createTextField(String texto,int x, int y, int width, int height) {
		JTextField textField = new JTextField();
		textField.setText(texto);
		textField.setBounds(x, y, width, height);
//		Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
//		textField.setCursor(cursor);
		return textField;
	}
	
	public static JPasswordField createPasswordField(int x, int y, int width, int height) {
		JPasswordField pwdField = new JPasswordField();
		pwdField.setBounds(x, y, width, height);
		return pwdField;
	}

//	public static JCheckBox createCheckBox(int x, int y, int width, int height) {
//		JCheckBox checkBox = new JCheckBox();
//		checkBox.setBounds(x, y, width, height);
//		checkBox.setOpaque(false);
//		return checkBox;
//	}
	
//	public static JComboBox<String> createComboBox(ArrayList<String> list, int x, int y, int width, int height){
//		JComboBox<String> comboBox = new JComboBox<String>();
//		
//		for(String l: list)comboBox.addItem(l);
//		
//		comboBox.setBounds(x, y, width, height);
//		
//		return comboBox;
//	}
	
//	public static JTable creteTable(int filas, int columnas, String[] columns) {
//		JTable table = new JTable();
//		table.setModel(new DefaultTableModel() {
//			private static final long serialVersionUID = 1L;
//			@Override
//			public boolean isCellEditable(int row, int col) {
//		         return false;
//		    }
//			@Override
//			 public String getColumnName(int index) {
//				return columns[index];
//			}
//			
//			@Override
//			public int getColumnCount() {
//		         return columnas;
//		    }
//			
//			 @Override
//			    public int getRowCount() {
//			         return filas;
//			    }
//		});
//		return table;
//	}
//	
//	public static JSpinner createSpinner(int x, int y, int width, int height) {
//		JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
//		spinner.setBounds(x, y, width, height);
//		return spinner;
//	}
}
