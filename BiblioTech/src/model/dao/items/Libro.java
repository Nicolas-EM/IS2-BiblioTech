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
import model.transfers.TransferLibro;
import model.utils.FieldValidator;

public class Libro extends SimulatedObject {
	public static String staticName = "Libros";

	public Libro() {
		this.Name = Libro.staticName;
	}

	@Override
	public Pair<Event, String> alta(Transfer _l) {
		Pair<Event, String> ret = new Pair<Event, String>(Event.MESSAGE, "BOOK REGISTERED! :D");
		
		TransferLibro l = (TransferLibro) _l;
		if(!FieldValidator.isValidISBN(l.getISBN())) {
			ret.set(Event.ERROR, "Invalid ISBN provided!");
			return ret;
		}
		if(l.getTitulo().isEmpty()) {
			ret.set(Event.ERROR, "Empty title!");
			return ret;
		}
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Libro WHERE ISBN = \"%s\" FOR UPDATE", l.getISBN());

		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next() ) {// si existe el libro en la bbdd o nuevo libro es invalido
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
				String insert = "INSERT INTO Libro(ISBN, Titulo, Cantidad) VALUES (?,?,?)";
				statement = conn.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);

				statement.setInt(1, l.getISBN());
				statement.setString(2, l.getTitulo());
				statement.setInt(3, l.getCantidad());

				statement.executeUpdate();
				statement.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			ret.set(Event.ERROR, "BOOK NOT REGISTERED! D:");
		}
		return ret;
	}

	@Override
	public Pair<Event, String> baja(String id) {// no tienen que ser void deberian devolver int o enum
		Pair<Event, String> ret = new Pair<Event, String>(Event.MESSAGE, "BOOK DELETED! :D");
		Connection conn = DatabaseConnection.getConnection();
		//String query = String.format("SELECT * FROM Libro WHERE id = '" + id + "' OR ISBN LIKE '" + id + "' OR Titulo LIKE '" + id + "' LIMIT 1");
		String query = String.format("SELECT * FROM Libro WHERE id = '" + id + "' LIMIT 1");
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {// si existe el libro en la bbdd lo borramos
				statement.close();
				resultSet.close();

				//String delete = "DELETE FROM Libro WHERE id = '" + id + "' OR ISBN LIKE '" + id + "' OR Titulo LIKE '" + id + "'";
				String delete = "DELETE FROM Libro WHERE id = '" + id + "'";
				statement = conn.prepareStatement(delete);

				statement.executeUpdate();
				statement.close();

				ret.set(Event.MESSAGE, "BOOK WITH Id: " + id + " DELETED! :D");
			} else {
				ret.set(Event.ERROR, ErrorCodes.EntityDoesNotExist.toString());
			}
			statement.close();
			resultSet.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			ret.set(Event.ERROR, "BOOK NOT FOUND! D:");
		}
		return ret;
	}

	@Override
	public Pair<Event, String> modificar(Transfer _l) {// tiene que pasar por parametro un TransferLibro
		Pair<Event, String> ret = new Pair<Event, String>(Event.MESSAGE, "BOOK UPDATED! :D");
		TransferLibro l = (TransferLibro) _l;
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Libro WHERE id = %d FOR UPDATE", l.getId());
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				
				statement.close();
				resultSet.close();

				String update = "UPDATE Libro SET ISBN = '" + l.getISBN() + "', Titulo = '"+ l.getTitulo() +"', Cantidad = " + l.getCantidad() + " WHERE id = " + l.getId();
				statement = conn.prepareStatement(update, PreparedStatement.RETURN_GENERATED_KEYS);

				statement.executeUpdate();
				statement.close();
			} else {
				statement.close();
				resultSet.close();
				ret.set(Event.ERROR, "BOOK NOT UPDATED! D:");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			ret.set(Event.ERROR, "BOOK NOT UPDATED! D:");
		}
		return ret;
	}

	@Override
	public TransferLibro mostrar(Transfer _l) {// tiene que devolver un transfer
		TransferLibro l = (TransferLibro) _l;// este es el parametro
		TransferLibro libro = null;// esto es lo que devuelve

		String query = "SELECT * FROM Libro WHERE id = " + l.getISBN() + "OR ISBN = " + l.getISBN();
		Connection conn = DatabaseConnection.getConnection();

		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {// si existe el libro que se quiere modificar
				libro = new TransferLibro(resultSet.getInt("id"), resultSet.getInt("ISBN"),
						resultSet.getString("Titulo"), resultSet.getInt("Cantidad"));
			} else {
				// Tirar error (va a ser devolver null y checar fuera por null)
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return libro;
	}
	
	@Override
	public TransferLibro get(String id) {
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Libro WHERE id = '" + id + "' OR ISBN LIKE '" + id + "' OR Titulo LIKE '" + id + "' LIMIT 1");
		TransferLibro l = null;
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {// si existe el libro en la bbdd
				l = new TransferLibro(resultSet.getInt("id"), resultSet.getInt("ISBN"),
						resultSet.getString("Titulo"), resultSet.getInt("Cantidad"));
			}
			statement.close();
			resultSet.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return l;
	}
	

	@Override
	public Pair<Event, String> reservar(String usuario, String id) {//prestar
		Pair<Event, String> ret = new Pair<Event, String>(Event.ERROR, "BOOK NOT FOUND! D:");
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Libro WHERE id = '" + id + "' LIMIT 1");
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {// si existe el libro en la bbdd 
				int cantidad = resultSet.getInt("Cantidad");
				statement.close();
				resultSet.close();

				if(cantidad > 0) {
					String reserve = "UPDATE Libro SET Cantidad = Cantidad - 1 WHERE id = '" + id + "'";
					statement = conn.prepareStatement(reserve);
					statement.executeUpdate();
					statement.close();
					
					reserve = "INSERT INTO ReservasLibros(Usuario, Libro) VALUES (?,?)";
					statement = conn.prepareStatement(reserve, PreparedStatement.RETURN_GENERATED_KEYS);

					statement.setString(1, usuario);
					statement.setString(2, id);
					statement.executeUpdate();
					statement.close();

					cantidad--;
					ret.set(Event.MESSAGE, "BOOK WITH Id: " + id + " RESERVED to " + usuario + "! Remaining: " + cantidad + " :D");
				}
				else {
					ret.set(Event.ERROR, "ERROR: Not enough books!!");
				}
				
			} else {
				statement.close();
				resultSet.close();
				ret.set(Event.ERROR, ErrorCodes.EntityDoesNotExist.toString());
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return ret;
	}
	
	@Override
	public Pair<Event, String> cancelar_reserva(String usuario, String id) {
		Pair<Event, String> ret = new Pair<Event, String>(Event.ERROR, "BOOK NOT FOUND! D:");
		Connection conn = DatabaseConnection.getConnection();
		String query = String.format("SELECT * FROM Libro WHERE id = '" + id + "' LIMIT 1");
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {// si existe el libro en la bbdd
				int cantidad = resultSet.getInt("Cantidad");
				statement.close();
				resultSet.close();

				query = "SELECT * FROM ReservasLibros WHERE Usuario = '" + usuario + "' AND Libro = '" + id + "' LIMIT 1";
				statement = conn.prepareStatement(query);
				resultSet = statement.executeQuery(query);
				if(resultSet.next()) {
					statement.close();
					resultSet.close();
					
					String delete = "UPDATE Libro SET Cantidad = Cantidad + 1 WHERE id = '" + id + "'";
					statement = conn.prepareStatement(delete);
					statement.executeUpdate();
					statement.close();
					
					delete = "DELETE FROM ReservasLibros WHERE Usuario = '"+ usuario +"' AND Libro = '" + id + "' LIMIT 1";//delete from reservas libro
					statement = conn.prepareStatement(delete);
					statement.executeUpdate();
					statement.close();
					
					cantidad++;
					ret.set(Event.MESSAGE, "BOOK WITH Id: " + id + " RETURNED! Remaining: " + cantidad + " :D");
				} else {
					statement.close();
					resultSet.close();
					ret.set(Event.ERROR, "BOOK WITH Id: " + id + " was not reserved by "+ usuario + "!");
				}
				

			} else {
				statement.close();
				resultSet.close();
				ret.set(Event.ERROR, ErrorCodes.EntityDoesNotExist.toString());
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return ret;
	}
	
	@Override
	public List<JButton> getButtonList() {
		List<JButton> btnList = new ArrayList<>();

		btnList.add(new JButton("PRESTAR"));
		btnList.add(new JButton("REGRESAR"));
		btnList.add(new JButton("MOSTRAR LIBROS"));
		//btnList.add(new JButton("RESERVAR"));
		//btnList.add(new JButton("CANCELAR RESERVA"));
//		btnList.add(new JButton("MULTAS"));

		if (UsuarioDAO.getInstance().getPermissions() == UserPermissions.ADMINISTRATOR
				|| UsuarioDAO.getInstance().getPermissions() == UserPermissions.LIBRARIAN) {
			btnList.add(new JButton("ALTA"));
			btnList.add(new JButton("MODIFICAR"));
			btnList.add(new JButton("BAJA"));
			btnList.add(new JButton("VER LIBRO"));
		}
			

		return btnList;
	}

	@Override
	public List<Event> getButtonEventList() {
		List<Event> eventList = new ArrayList<>();

		eventList.add(Event.SHOW_SEARCH_FOR_RESERVATION);
		eventList.add(Event.SHOW_SEARCH_FOR_CANCEL_RESERVATION);
		//eventList.add(Event.CREAR_RESERVA_LIBRO);
		eventList.add(Event.LISTAR_LIBROS);
		//eventList.add(Event.DEL_RESERVA_LIBRO);
//		eventList.add(Event.MULTAS);

		if (UsuarioDAO.getInstance().getPermissions() == UserPermissions.ADMINISTRATOR
				|| UsuarioDAO.getInstance().getPermissions() == UserPermissions.LIBRARIAN) {
			eventList.add(Event.SHOW_ALTA);
			eventList.add(Event.SHOW_SEARCH_FOR_MODIFY);
			eventList.add(Event.SHOW_SEARCH_FOR_DELETE);
			eventList.add(Event.MOSTRAR_LIBRO);
			
		}
		
		return eventList;
	}
	
	@Override
	public List<JLabel> getLabelListAlta(int width) {
		List<JLabel> labelList = new ArrayList<>();

		labelList.add(ComponentsBuilder.createLabel("ISBN", width/2 - 100, 25, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14)));
		labelList.add(ComponentsBuilder.createLabel("Title", width/2 - 100, 50, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14)));
		labelList.add(ComponentsBuilder.createLabel("Cantidad", width/2 - 100, 75, 100, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14)));

		return labelList;
	}

	@Override
	public List<JTextField> getTextFieldListAlta(int width) {
		List<JTextField> textFieldList = new ArrayList<>();
		JTextField isbnField = ComponentsBuilder.createTextField(width/2, 40, 150, 20);
		isbnField.setDocument(new JTextFieldCharLimit(10));
		textFieldList.add(isbnField);
		
		JTextField titleField = ComponentsBuilder.createTextField(width/2, 65, 150, 20);
		titleField.setDocument(new JTextFieldCharLimit(32));
		textFieldList.add(titleField);

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
					String isbnString = textFields.get(0).getText();
					if(FieldValidator.isParsableToInt(textFields.get(0).getText()) || FieldValidator.isValidISBN(isbnString)) {//cantidad & ISBN fields
						int isbn = Integer.parseInt(textFields.get(0).getText());		// ISBN field
						int cantidad = Integer.parseInt(textFields.get(2).getText());	// cantidad field
						Controller.getInstance().action(Event.ALTA_LIBRO, new TransferLibro(isbn, textFields.get(1).getText(), cantidad));
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
		return ComponentsBuilder.createLabel("Buscar por ID/ISBN/Titulo", width/2 - 200, 25, 200, 50, Color.BLACK, new Font("Arial", Font.BOLD, 14));
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
					Controller.getInstance().action(Event.BUSCAR_LIBRO, new Pair<String, Event>(textField.getText(), evnt));
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
					if(FieldValidator.isParsableToInt(textFields.get(2).getText()) && FieldValidator.isParsableToInt(textFields.get(0).getText())) {	//cantidad & ISBN fields
						int isbn = Integer.parseInt(textFields.get(0).getText());		// ISBN field
						int cantidad = Integer.parseInt(textFields.get(2).getText());	// cantidad field	
						Controller.getInstance().action(Event.MODIFICAR_LIBRO, new TransferLibro(id, isbn, textFields.get(1).getText(), cantidad));
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
	public ActionListener getActionListenerReserva(List<JTextField> textFields, Event evnt, int id) {
		ActionListener listenerModificar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textFields.get(0).getText().equals(""))
					Controller.getInstance().action(Event.ERROR, "All fields are mandatory");
				else {
					String name = textFields.get(0).getText();	//user dni field
					if(FieldValidator.isValidDNI(name)) {
						Pair<String, Integer> res = new Pair<String, Integer>(name, id);
						Controller.getInstance().action(evnt, res);
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
		return Event.BAJA_LIBRO;
	}
	
	@Override
	public Event getEventReservar() {
		return Event.PRESTAR;
	}

	@Override
	public Event getEventCancelarReserva() {
		return Event.REGRESAR;
	}

	@Override
	public List<String> getAllID() {
		String query = String.format("SELECT Titulo FROM Libro");
		Connection conn = DatabaseConnection.getConnection();
		List<String> idList = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				idList.add(resultSet.getString("Titulo"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return idList;
	}
	
}
