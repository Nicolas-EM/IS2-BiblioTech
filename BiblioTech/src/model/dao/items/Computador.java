package model.dao.items;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import control.ErrorCodes;
import control.UserPermissions;
import control.controller.Controller;
import control.events.Event;
import gui.utils.ComponentsBuilder;
import gui.utils.JTextFieldCharLimit;
import gui.utils.Pair;
import integration.connection.DatabaseConnection;
import model.dao.SimulatedObject;
import model.dao.UsuarioDAO;
import model.transfers.Transfer;
import model.transfers.TransferComputador;
import model.utils.FieldValidator;

public class Computador extends SimulatedObject {
	public static String staticName = "Computadores";

	public Computador() {
		this.Name = Computador.staticName;
	}

	@Override
	public Pair<Event, String> alta(Transfer _c) {
		Pair<Event, String> ret = new Pair<Event, String>(Event.MESSAGE, "COMPUTER REGISTERED! :D");
		TransferComputador c = (TransferComputador)_c; 
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Computador WHERE Modelo = \"%s\" FOR UPDATE", c.getModelo());
		if(c.getMarca()!="" && c.getModelo()!= "" && c.getCantidad() > 0 ) {
			try {
				PreparedStatement statement = conn.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery(query);

				if (resultSet.next()) {
				
					statement.close();
					resultSet.close();
				
					ret.set(Event.ERROR, ErrorCodes.EntityAlreadyExists.toString());
					} else {
						statement.close();
						resultSet.close();
						// query = "INSERT INTO Libro(ISBN, Titulo, Cantidad) VALUES (" + l.getISBN() +
						// ", "+ l.getTitulo() +", "+ l.getCantidad() +")";
						String insert = "INSERT INTO Computador(Marca, Modelo, Cantidad) VALUES (?,?,?)";
						statement = conn.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);

						statement.setString(1, c.getMarca());
						statement.setString(2, c.getModelo());
						statement.setInt(3, c.getCantidad());

						statement.executeUpdate();
						statement.close();
						}
				} catch (SQLException ex) {
					ex.printStackTrace();
					ret.set(Event.ERROR, "COMPUTER NOT REGISTERED! D:");
					}
			}else {
				ret.set(Event.ERROR, "All fields are mandatory");
			}
		return ret;
	}

	@Override
	public Pair<Event, String> baja(String Id) {
		Pair<Event, String> ret = new Pair<Event, String>(Event.MESSAGE, "COMPUTER DELETED! :D");
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Computador WHERE id = '" + Id + "' LIMIT 1");
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {// si existe el libro en la bbdd lo borramos
				statement.close();
				resultSet.close();

				//String delete = "DELETE FROM Libro WHERE id = '" + id + "' OR ISBN LIKE '" + id + "' OR Titulo LIKE '" + id + "'";
				String delete = "DELETE FROM Computador WHERE id = '" + Id + "'";
				statement = conn.prepareStatement(delete);

				statement.executeUpdate();
				statement.close();

				ret.set(Event.MESSAGE, "COMPUTER WITH Id: " + Id + " DELETED! :D");
				Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Computador());
			} else {
				ret.set(Event.ERROR, ErrorCodes.EntityDoesNotExist.toString());
			}
			statement.close();
			resultSet.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			ret.set(Event.ERROR, "COMPUTER NOT FOUND! D:");
		}
		return ret;
	}

	@Override
	public Pair<Event, String> modificar(Transfer _c) {
		Pair<Event, String> ret = new Pair<Event, String>(Event.MESSAGE, "COMPUTER MODIFIED! :D");
		TransferComputador c = (TransferComputador)_c;
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Computador WHERE id = %d FOR UPDATE", c.getId());
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				
				statement.close();
				resultSet.close();

				String update = "UPDATE Computador SET Modelo = '" + c.getModelo() + "', Marca = '"+ c.getMarca() +"', Cantidad = " + c.getCantidad() + " WHERE id = " + c.getId();
				statement = conn.prepareStatement(update, PreparedStatement.RETURN_GENERATED_KEYS);

				statement.executeUpdate();
				statement.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			ret.set(Event.ERROR, "COMPUTER NOT UPDATED! D:");
		}
		return ret;
	}

	@Override
	public TransferComputador mostrar(Transfer _c) {
		TransferComputador c = (TransferComputador)_c;
		TransferComputador ret = null;//este metodo devolver un TransferComputador
		
		String query = "SELECT * FROM Computador WHERE id = "+ c.getId()+ "OR Modelo = "+ c.getModelo();
		Connection conn = DatabaseConnection.getConnection();

		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) {
				ret = new TransferComputador(resultSet.getInt("id"), resultSet.getString("Modelo"),
						resultSet.getString("Marca"), resultSet.getInt("Cantidad"));
			}
		
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	@Override
	public TransferComputador get(String id) {
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Computador WHERE id = '"+id+"' OR Modelo LIKE '" + id + "' LIMIT 1");
		TransferComputador c = null;
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) {
				c = new TransferComputador(resultSet.getInt("id"), resultSet.getString("Marca"),
						resultSet.getString("Modelo"), resultSet.getInt("Cantidad"));
			}
			statement.close();
			resultSet.close();
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		return c;
	}
	
	@Override
	public Pair<Event, String> reservar(String usuario, String id) {
		Pair<Event, String> ret = new Pair<Event, String>(Event.ERROR, "COMPUTER NOT FOUND! D:");
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Computador WHERE id = '" + id + "' LIMIT 1");
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {// si existe el libro en la bbdd 
				int cantidad = resultSet.getInt("Cantidad");
				statement.close();
				resultSet.close();

				if(cantidad > 0) {
					String reserve = "UPDATE Computador SET Cantidad = Cantidad - 1 WHERE id = '" + id + "'";
					statement = conn.prepareStatement(reserve);
					statement.executeUpdate();
					statement.close();
					
					reserve = "INSERT INTO ReservaComputador(Usuario, Computador) VALUES (?,?)";
					statement = conn.prepareStatement(reserve, PreparedStatement.RETURN_GENERATED_KEYS);

					statement.setString(1, usuario);
					statement.setString(2, id);
					statement.executeUpdate();
					statement.close();

					cantidad--;
					ret.set(Event.MESSAGE, "COMPUTER WITH Id: " + id + " RESERVED to " + usuario + "! Remaining: " + cantidad + " :D");
				}
				else {
					ret.set(Event.ERROR, "ERROR: Not enough computers!!");
				}
			} else {
				statement.close();
				resultSet.close();
				ret.set(Event.ERROR, ErrorCodes.EntityDoesNotExist.toString());
			}	
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	@Override
	public Pair<Event, String> cancelar_reserva(String usuario, String id) {
		Pair<Event, String> ret = new Pair<Event, String>(Event.ERROR, "COMPUTER NOT FOUND! D:");
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Computador WHERE id = '" + id + "' LIMIT 1");
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			
			if (resultSet.next()) {// si existe el libro en la bbdd
				int cantidad = resultSet.getInt("Cantidad");
				statement.close();
				resultSet.close();

				query = "SELECT * FROM ReservaComputador WHERE Usuario = '" + usuario + "' AND Computador = '" + id + "' LIMIT 1";
				statement = conn.prepareStatement(query);
				resultSet = statement.executeQuery(query);
				if(resultSet.next()) {
					statement.close();
					resultSet.close();
					
					String delete = "UPDATE Computador SET Cantidad = Cantidad + 1 WHERE id = '" + id + "'";
					statement = conn.prepareStatement(delete);
					statement.executeUpdate();
					statement.close();
					
					delete = "DELETE FROM ReservaComputador WHERE Usuario = '"+ usuario +"' AND Computador = '" + id + "' LIMIT 1";//delete from reservas libro
					statement = conn.prepareStatement(delete);
					statement.executeUpdate();
					statement.close();
					
					cantidad++;
					ret.set(Event.MESSAGE, "COMPUTER WITH Id: " + id + " RETURNED! Remaining: " + cantidad + " :D");
				} else {
					statement.close();
					resultSet.close();
					ret.set(Event.ERROR, "COMPUTER WITH Id: " + id + " was not reserved by "+ usuario + "!");
				}

			} else {
				statement.close();
				resultSet.close();
				ret.set(Event.ERROR, ErrorCodes.EntityDoesNotExist.toString());
			}		
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return ret;
	}
	
	@Override
	public List<JButton> getButtonList() {
		List<JButton> btnList = new ArrayList<>();

		btnList.add(new JButton("RESERVAR"));
		btnList.add(new JButton("CANCELAR RESERVAR"));
		btnList.add(new JButton("LISTA DE COMPUTADORES"));

		if(UsuarioDAO.getInstance().getPermissions() == UserPermissions.ADMINISTRATOR ||UsuarioDAO.getInstance().getPermissions() == UserPermissions.LIBRARIAN)
			{
				btnList.add(new JButton("ALTA"));
				btnList.add(new JButton("BAJA"));
				btnList.add(new JButton("MODIFICAR"));
				btnList.add(new JButton("VER PC"));
			}

		return btnList;
	}

	@Override
	public List<Event> getButtonEventList() {
		List<Event> eventList = new ArrayList<>();

		eventList.add(Event.SHOW_SEARCH_FOR_RESERVATION);
		eventList.add(Event.SHOW_SEARCH_FOR_CANCEL_RESERVATION);
		eventList.add(Event.LISTAR_PCS);

		if(UsuarioDAO.getInstance().getPermissions() == UserPermissions.ADMINISTRATOR || UsuarioDAO.getInstance().getPermissions() == UserPermissions.LIBRARIAN) {
			eventList.add(Event.SHOW_ALTA);
			eventList.add(Event.SHOW_SEARCH_FOR_DELETE);
			eventList.add(Event.SHOW_SEARCH_FOR_MODIFY);
			eventList.add(Event.MOSTRAR_PC);
		}
		return eventList;
	}

	@Override
	public List<JLabel> getLabelListAlta(int width) {
		List<JLabel> labelList = new ArrayList<>();

		labelList.add(ComponentsBuilder.createLabel("Marca", width/2 - 100, 25, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14)));
		labelList.add(ComponentsBuilder.createLabel("Modelo", width/2 - 100, 50, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14)));
		labelList.add(ComponentsBuilder.createLabel("Cantidad", width/2 - 100, 75, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14)));

		return labelList;
	}

	@Override
	public List<JTextField> getTextFieldListAlta(int width) {
		List<JTextField> textFieldList = new ArrayList<>();
		JTextField marcaField = ComponentsBuilder.createTextField(width/2, 40, 150, 20);
		marcaField.setDocument(new JTextFieldCharLimit(9));
		textFieldList.add(marcaField);
		
		JTextField modeloField = ComponentsBuilder.createTextField(width/2, 65, 150, 20);
		modeloField.setDocument(new JTextFieldCharLimit(32));
		textFieldList.add(modeloField);

		JTextField cantidadField = ComponentsBuilder.createTextField(width/2, 90, 150, 20);
		cantidadField.setDocument(new JTextFieldCharLimit(32));
		textFieldList.add(cantidadField);
		
		return textFieldList;
	}
	
	@Override
	public ActionListener getActionListenerAlta(List<JTextField> textFields) {
		ActionListener listenerAlta = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textFields.get(0).getText().equals("") || textFields.get(1).getText().equals("") || textFields.get(2).getText().equals(""))
					Controller.getInstance().action(Event.ERROR, "All fields are mandatory");
				else {
					if(FieldValidator.isParsableToInt(textFields.get(2).getText())) {//cantidad field
						int cantidad = Integer.parseInt(textFields.get(2).getText());//cantidad field
						Controller.getInstance().action(Event.ALTA_COMPUTADOR, new TransferComputador(textFields.get(0).getText(), textFields.get(1).getText(), cantidad));
						for(int i = 0; i < textFields.size(); i++)
							textFields.get(i).setText("");
					}
					else {
						Controller.getInstance().action(Event.ERROR, "Invalid number");
					}
				}		
			}
		};
		return listenerAlta;
	}

	@Override
	public JLabel getLabelSearch(int width) {
		return ComponentsBuilder.createLabel("Buscar por ID/Modelo", width/2 - 200, 25, 200, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14));

	}

	@Override
	public JTextField getTextFieldSearch(int width) {
		JTextField searchField = ComponentsBuilder.createTextField(width/2, 40, 150, 20);
		searchField.setDocument(new JTextFieldCharLimit(32));
		return searchField;
	}

	@Override
	public ActionListener getActionListenerSearch(JTextField textField, Event evnt) {
		ActionListener listenerSearch = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals(""))
					Controller.getInstance().action(Event.ERROR, "All fields are mandatory");
				else {
					Controller.getInstance().action(Event.BUSCAR_COMPUTADOR, new Pair<String, Event>(textField.getText(), evnt));
					textField.setText("");//search by id isbn or title
				}
			}
		};
		return listenerSearch;
	}

	@Override
	public ActionListener getActionListenerModificar(List<JTextField> textFields, int id) {
		ActionListener listenerModificar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textFields.get(0).getText().equals("") || textFields.get(1).getText().equals("") || textFields.get(2).getText().equals(""))
					Controller.getInstance().action(Event.ERROR, "All fields are mandatory");
				else {
					// (mirar cuales son los textfields en getTextFieldListAlta)
					if(FieldValidator.isParsableToInt(textFields.get(2).getText())) {	//cantidad & ISBN fields
						int cantidad = Integer.parseInt(textFields.get(2).getText());	// cantidad field	
						Controller.getInstance().action(Event.MODIFICAR_COMPUTADOR, new TransferComputador(id, textFields.get(0).getText(), textFields.get(1).getText(), cantidad));
					}
					else {
						Controller.getInstance().action(Event.ERROR, "Invalid field(s)");
					}
				}		
			}
		};
		return listenerModificar;
	}
	
	@Override
	public List<JLabel> getLabelListReserva(int width) {
		List<JLabel> labelList = new ArrayList<>();

		labelList.add(ComponentsBuilder.createLabel("User DNI", width/2 - 100, 25, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14)));
		return labelList;
	}

	@Override
	public List<JTextField> getTextFieldListReserva(int width) {
		List<JTextField> textFieldList = new ArrayList<>();
		JTextField userField = ComponentsBuilder.createTextField(width/2, 40, 150, 20);
		userField.setDocument(new JTextFieldCharLimit(9));
		textFieldList.add(userField);
		return textFieldList;
	}
	
	@Override
	public ActionListener getActionListenerReserva(List<JTextField> textFields, Event event, int id) {
		ActionListener listenerModificar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textFields.get(0).getText().equals(""))
					Controller.getInstance().action(Event.ERROR, "All fields are mandatory");
				else {
					String name = textFields.get(0).getText();	//user dni field
					if(FieldValidator.isValidDNI(name)) {
						Pair<String, Integer> res = new Pair<String, Integer>(name, id);
						Controller.getInstance().action(event, res);
						for(int i = 0; i < textFields.size(); i++)
							textFields.get(i).setText("");
					} else {
						Controller.getInstance().action(Event.ERROR, "Invalid DNI");
					}
				}		
			}
		};
		return listenerModificar;
	}
	
	@Override
	public Event getEventBaja() {
		return Event.BAJA_COMPUTADOR;
	}
	
	@Override
	public Event getEventReservar() {
		return Event.CREAR_RESERVA_COMPUTADOR;
	}

	@Override
	public Event getEventCancelarReserva() {
		return Event.DEL_RESERVA_COMPUTADOR;
	}

	@Override
	public List<String> getAllID() {
		String query = String.format("SELECT id FROM Computador");
		Connection conn = DatabaseConnection.getConnection();
		List<String> idList = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				idList.add(resultSet.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return idList;
	}
}
