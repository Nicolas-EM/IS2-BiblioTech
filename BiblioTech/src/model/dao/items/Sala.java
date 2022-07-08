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
import model.transfers.TransferSala;
import model.utils.FieldValidator;

public class Sala extends SimulatedObject {
	public static String staticName = "Salas";

	public Sala() {
		this.Name = Sala.staticName;
	}

	@Override
	public Pair<Event, String> alta(Transfer _s) {
		Pair<Event, String> ret = new Pair<Event, String>(Event.MESSAGE, "SALA REGISTERED! :D");
		TransferSala s = (TransferSala)_s;
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Sala WHERE Nombre = \"%s\" FOR UPDATE", s.getNombre());
		if(s.getNombre().isEmpty()) {
			ret.set(Event.ERROR, "EMPTY SALA NAME!");
			return ret;
		}
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {// si existe el libro en la bbdd
				// query = "UPDATE Libro SET Cantidad = Cantidad + " + l.getCantidad();
				// statement.executeQuery(query);
				// tirar error ya existe el libro.
				
				statement.close();
				resultSet.close();
				
				ret.set(Event.ERROR, ErrorCodes.EntityAlreadyExists.toString());
			} else {
				statement.close();
				resultSet.close();
				// query = "INSERT INTO Libro(ISBN, Titulo, Cantidad) VALUES (" + l.getISBN() +
				// ", "+ l.getTitulo() +", "+ l.getCantidad() +")";
				String insert = "INSERT INTO Sala(Nombre, Cantidad) VALUES (?,?)";
				statement = conn.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);

				statement.setString(1, s.getNombre());
				
				statement.setInt(2, s.getCantidad());

				statement.executeUpdate();
				statement.close();
				
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			ret.set(Event.ERROR, "SALA NOT REGISTERED! D:");
		}
		return ret;
	}

	@Override
	public Pair<Event, String> baja(String Id) {
		Pair<Event, String> ret = new Pair<Event, String>(Event.MESSAGE, "SALA DELETED! :D");
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Sala WHERE id = '" + Id + "' LIMIT 1");		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			
			if (resultSet.next()) {//si existe el libro en la bbdd
				statement.close();
				resultSet.close();
				
				String delete = "DELETE FROM Sala WHERE id = '" + Id + "'";
				statement = conn.prepareStatement(delete);
				
				statement.executeUpdate();
				statement.close();
				
				ret.set(Event.MESSAGE, "SALA WITH Id: " + Id + " DELETED! :D");
				//Controller.getInstance().action(Event.UPDATE_GUI_MRP_ITEM, new Sala());
			} else {
				ret.set(Event.ERROR, ErrorCodes.EntityDoesNotExist.toString());
			}
			statement.close();
			resultSet.close();
		} catch(SQLException ex) {
			ex.printStackTrace();
			ret.set(Event.ERROR, "SALA NOT FOUND! D:");
		}
		return ret;
	}

	@Override
	public Pair<Event, String> modificar(Transfer _s) {
		Pair<Event, String> ret = new Pair<Event, String>(Event.MESSAGE, "SALA MODIFIED! :D");
		TransferSala s = (TransferSala)_s;

		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Sala WHERE id = %d FOR UPDATE", s.getId());
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				
				statement.close();
				resultSet.close();

				String update = "UPDATE Sala SET Nombre = '" + s.getNombre() + "', Cantidad = " + s.getCantidad() + " WHERE id = " + s.getId();
				statement = conn.prepareStatement(update, PreparedStatement.RETURN_GENERATED_KEYS);

				statement.executeUpdate();
				statement.close();
			} else {
				statement.close();
				resultSet.close();
				ret.set(Event.ERROR, "SALA DOES NOT EXIST! D:");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			ret.set(Event.ERROR, "SALA NOT UPDATED! D:");
		}
		
		return ret;
	}

	@Override
	protected TransferSala mostrar(Transfer _s) {
		TransferSala s = (TransferSala)_s;
		TransferSala ret = null;//debe devolver un transfer sala
		
		String query = "SELECT * FROM Salas WHERE id = " + s.getId() + "OR Nombre = " + s.getNombre();
		Connection conn = DatabaseConnection.getConnection();
		
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) {//si existe el libro que se quiere modificar
			ret	 = new TransferSala(resultSet.getInt("id"), resultSet.getString("Nombre"),
						resultSet.getInt("Cantidad"));
			} else {
				//Tirar error (va a ser devolver null y checar fuera por null)
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		
		return ret;
	}
	
	@Override
	public TransferSala get(String id) {
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Sala WHERE id = '" + id + "' OR Nombre LIKE '" + id +  "' LIMIT 1");
		TransferSala s = null;
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) {
				s = new TransferSala(resultSet.getInt("id"), resultSet.getString("Nombre"),
					 resultSet.getInt("Cantidad"));
			}
			statement.close();
			resultSet.close();
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return s;
	}
	
	@Override
	public Pair<Event, String> reservar(String usuario, String id) {
		Pair<Event, String> ret = new Pair<Event, String>(Event.ERROR, "SALA NOT FOUND! D:");
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Sala WHERE id = '" + id + "' LIMIT 1");
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {// si existe el libro en la bbdd 
				int cantidad = resultSet.getInt("Cantidad");
				statement.close();
				resultSet.close();

				if(cantidad > 0) {
					String reserve = "UPDATE Sala SET Cantidad = Cantidad - 1 WHERE id = '" + id + "'";
					statement = conn.prepareStatement(reserve);
					statement.executeUpdate();
					statement.close();
					
					reserve = "INSERT INTO ReservaSala(Usuario, Sala) VALUES (?,?)";
					statement = conn.prepareStatement(reserve, PreparedStatement.RETURN_GENERATED_KEYS);

					statement.setString(1, usuario);
					statement.setString(2, id);
					statement.executeUpdate();
					statement.close();

					cantidad--;
					ret.set(Event.MESSAGE, "SALA WITH Id: " + id + " RESERVED to " + usuario + "! Remaining: " + cantidad + " :D");
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
		Pair<Event, String> ret = new Pair<Event, String>(Event.ERROR, "SALA NOT FOUND! D:");
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Sala WHERE id = '" + id + "' LIMIT 1");
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			
			if (resultSet.next()) {// si existe el libro en la bbdd
				int cantidad = resultSet.getInt("Cantidad");
				statement.close();
				resultSet.close();

				query = "SELECT * FROM ReservaSala WHERE Usuario = '" + usuario + "' AND Sala = '" + id + "' LIMIT 1";
				statement = conn.prepareStatement(query);
				resultSet = statement.executeQuery(query);
				if(resultSet.next()) {
					statement.close();
					resultSet.close();
					
					String delete = "UPDATE Sala SET Cantidad = Cantidad + 1 WHERE id = '" + id + "'";
					statement = conn.prepareStatement(delete);
					statement.executeUpdate();
					statement.close();
					
					delete = "DELETE FROM ReservaSala WHERE Usuario = '"+ usuario +"' AND Sala = '" + id + "' LIMIT 1";//delete from reservas libro
					statement = conn.prepareStatement(delete);
					statement.executeUpdate();
					statement.close();
					
					cantidad++;
					ret.set(Event.MESSAGE, "SALA WITH Id: " + id + " RETURNED! Remaining: " + cantidad + " :D");
				} else {
					statement.close();
					resultSet.close();
					ret.set(Event.ERROR, "SALA WITH Id: " + id + " was not reserved by "+ usuario + "!");
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
		btnList.add(new JButton("LISTA DE SALAS"));

		if(UsuarioDAO.getInstance().getPermissions() == UserPermissions.ADMINISTRATOR || UsuarioDAO.getInstance().getPermissions() == UserPermissions.LIBRARIAN) {
			btnList.add(new JButton("ALTA"));
			btnList.add(new JButton("MODIFICAR"));
			btnList.add(new JButton("BAJA"));
			btnList.add(new JButton("VER SALA"));
		}

		return btnList;
	}

	@Override
	public List<Event> getButtonEventList() {
		List<Event> eventList = new ArrayList<>();

		eventList.add(Event.SHOW_SEARCH_FOR_RESERVATION);
		eventList.add(Event.SHOW_SEARCH_FOR_CANCEL_RESERVATION);
		eventList.add(Event.LISTAR_SALAS);

		if(UsuarioDAO.getInstance().getPermissions() == UserPermissions.ADMINISTRATOR || UsuarioDAO.getInstance().getPermissions() == UserPermissions.LIBRARIAN) {
			eventList.add(Event.SHOW_ALTA);
			eventList.add(Event.SHOW_SEARCH_FOR_MODIFY);
			eventList.add(Event.SHOW_SEARCH_FOR_DELETE);
			eventList.add(Event.MOSTRAR_SALA);
		}

		return eventList;
	}

	@Override
	public List<JLabel> getLabelListAlta(int width) {
		List<JLabel> labelList = new ArrayList<>();

		labelList.add(ComponentsBuilder.createLabel("Nombre", width/2 - 100, 25, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14)));
		labelList.add(ComponentsBuilder.createLabel("Capacidad", width/2 - 100, 50, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14)));

		return labelList;
	}
	
	@Override
	public List<JTextField> getTextFieldListAlta(int width) {
		List<JTextField> textFieldList = new ArrayList<>();
		JTextField nombreField = ComponentsBuilder.createTextField(width/2, 40, 150, 20);
		nombreField.setDocument(new JTextFieldCharLimit(9));
		textFieldList.add(nombreField);
		
		JTextField cantidadField = ComponentsBuilder.createTextField(width/2, 65, 150, 20);
		cantidadField.setDocument(new JTextFieldCharLimit(32));
		textFieldList.add(cantidadField);
		
		return textFieldList;
	}

	@Override
	public ActionListener getActionListenerAlta(List<JTextField> textFields) {
		ActionListener listenerAlta = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textFields.get(0).getText().equals("") || textFields.get(1).getText().equals(""))
					Controller.getInstance().action(Event.ERROR, "All fields are mandatory");
				else {
					if(FieldValidator.isParsableToInt(textFields.get(1).getText())) {//cantidad field
						int cantidad = Integer.parseInt(textFields.get(1).getText());//cantidad field
						Controller.getInstance().action(Event.ALTA_SALA, new TransferSala(textFields.get(0).getText(), cantidad));
						for(int i = 0; i < textFields.size(); i++)
							textFields.get(i).setText("");
					}
					else {
						Controller.getInstance().action(Event.ERROR, "Invalid field(s)");
					}
				}		
			}
		};
		return listenerAlta;
	}

	@Override
	public JLabel getLabelSearch(int width) {
		// TODO Auto-generated method stub
		return ComponentsBuilder.createLabel("Buscar por ID/Nombre", width/2 - 200, 25, 200, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14));
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
					Controller.getInstance().action(Event.BUSCAR_SALA, new Pair<String, Event>(textField.getText(), evnt));
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
				if(textFields.get(0).getText().equals("") || textFields.get(1).getText().equals(""))
					Controller.getInstance().action(Event.ERROR, "All fields are mandatory");
				else {
					// (mirar cuales son los textfields en getTextFieldListAlta)
					if(FieldValidator.isParsableToInt(textFields.get(1).getText())) {	//cantidad & ISBN fields
						int cantidad = Integer.parseInt(textFields.get(1).getText());	// cantidad field	
						Controller.getInstance().action(Event.MODIFICAR_SALA, new TransferSala(id, textFields.get(0).getText(), cantidad));
					}
					else {
						Controller.getInstance().action(Event.ERROR, "Invalid field(s)");
					}
				}		
			}
		};
		return listenerModificar;	}
	
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
		return Event.BAJA_SALA;
	}
	
	@Override
	public Event getEventReservar() {
		return Event.CREAR_RESERVA_SALA;
	}

	@Override
	public Event getEventCancelarReserva() {
		return Event.DEL_RESERVA_SALA;
	}

	@Override
	public List<String> getAllID() {
		String query = String.format("SELECT id FROM Sala");
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
