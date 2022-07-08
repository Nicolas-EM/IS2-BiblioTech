package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import control.UserPermissions;
import control.controller.Controller;
import control.events.Event;
import integration.connection.DatabaseConnection;
import model.services.GUIService;
import model.transfers.TransferUsuario;
import model.utils.FieldValidator;

public class UsuarioDAOImp extends UsuarioDAO {
	
	@Override
	public List<String> getAllDNI() {
		String query = String.format("SELECT dni FROM Usuario");
		Connection conn = DatabaseConnection.getConnection();
		
		List<String> dniList = new ArrayList<>();
		
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				dniList.add(resultSet.getString("dni"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return dniList;
	}
	
	@Override
	public TransferUsuario getUsuario(String dni_key) {
		String query = String.format("SELECT * FROM Usuario WHERE dni = \"%s\" FOR UPDATE", dni_key);
		Connection conn = DatabaseConnection.getConnection();

		TransferUsuario usuario = null;
		
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			if (resultSet.next()) {
				usuario = new TransferUsuario(resultSet.getInt("id"), resultSet.getString("dni"), resultSet.getString("password"), resultSet.getString("nombre"), resultSet.getString("apellidos"), resultSet.getDate("fecha_nacimiento"), resultSet.getString("correo"), UserPermissions.valueOf(resultSet.getString("permisos").toUpperCase()));
			}
			
			resultSet.close();
			statement.close();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return usuario;
	}
	
	@Override
	public boolean authenticate(TransferUsuario user) {
		String dni = user.getDni();
		String password = user.getPwd();
		if(!FieldValidator.isValidDNI(dni)) {
			Controller.getInstance().action(Event.ERROR, "Invalid field(s)");
			return false;
		}
		try {
			user = getUsuario(dni);
			if(password.equals(user.getPwd())) {
				dni_key = getUsuario(dni).getDni();
				UsuarioDAO.permissions = user.getPermissions();
				return true;
			}
		}
		catch (Exception e){}
		UsuarioDAO.instance = null;
		return false;
	}
	
	@Override
	public boolean alta(TransferUsuario user) {
		if(!FieldValidator.isValidDNI(user.getDni()) || !FieldValidator.isValidEmail(user.getCorreo()) || user.getPwd() == "" || user.getName() == "" || user.getSurname() == "") {
			return false;
		}
		else if(user.getFnacimiento() != null && (new java.util.Date(user.getFnacimiento().getTime())).after(new java.util.Date())) {
			return false;
		}
		else {
			Connection conn = DatabaseConnection.getConnection();
			String insert = "INSERT INTO Usuario(dni, password, nombre, apellidos, fecha_nacimiento, correo, permisos) VALUES (?, ?, ?, ?, ?, ?, ?)";

			try {
				PreparedStatement statement = conn.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);

				statement.setString(1, user.getDni());
				statement.setString(2, user.getPwd());
				statement.setString(3, user.getName());
				statement.setString(4, user.getSurname());
				statement.setDate(5, user.getFnacimiento());
				statement.setString(6, user.getCorreo());
				if (UsuarioDAO.permissions == UserPermissions.ADMINISTRATOR)
					statement.setString(7, UserPermissions.LIBRARIAN.toString());
				else
					statement.setString(7, UsuarioDAO.permissions.toString());

				statement.executeUpdate();
				statement.close();

				return true;
			} catch (SQLException ex) {
 				return false;
			}
		}
	}

	@Override
	public boolean baja(TransferUsuario user) {
		String _dni;
		
		if(user != null) {
			_dni = user.getDni();
			if(!FieldValidator.isValidDNI(_dni) || UsuarioDAO.permissions == getUsuario(_dni).getPermissions() || getUsuario(_dni).getPermissions() == UserPermissions.ADMINISTRATOR)
				return false;
		}
		else if(this.getDni() == null){
			// algun error
			return false;
		}
		else {
			// Eliminar usuario propio
			_dni = this.getDni();
		}

		Connection conn = DatabaseConnection.getConnection();
		String delete = "DELETE FROM Usuario WHERE DNI = ?";

		try {
			PreparedStatement statement = conn.prepareStatement(delete, PreparedStatement.RETURN_GENERATED_KEYS);

			statement.setString(1, _dni);

			statement.executeUpdate();
			statement.close();
			
			if(this.getPermissions() == UserPermissions.USER) {
				this.logout();
				GUIService.getInstance().exit();
			}
			
			return true;
		} catch (SQLException ex) {}
		
		return false;
	}

	@Override
	public boolean modificar(TransferUsuario user) {
		if(!FieldValidator.isValidEmail(user.getCorreo()) || user.getName() == "" || user.getSurname() == ""){
			return false;
		}
		else {
			Connection conn = DatabaseConnection.getConnection();
			String UPDATE = "UPDATE Usuario SET nombre = ?, apellidos = ?, correo = ? WHERE dni =  ?";
			
			try {
				PreparedStatement statement = conn.prepareStatement(UPDATE, PreparedStatement.RETURN_GENERATED_KEYS);

				statement.setString(1, user.getName());
				statement.setString(2, user.getSurname());
				statement.setString(3, user.getCorreo());
				statement.setString(4, user.getDni());
				
				int result = statement.executeUpdate();
				statement.close();

				return result == 0 || result == -1? false : true;
			} catch (SQLException ex) {
				return false;
			}
		}
	}

	@Override
	public void mostrar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<JButton> getButtonList() {
		List<JButton> btnList = new ArrayList<>();	
		
		btnList.add(new JButton("DELETE USER"));
		btnList.add(new JButton("EDIT USER"));
		if(UsuarioDAO.getInstance().getPermissions() == UserPermissions.ADMINISTRATOR || UsuarioDAO.getInstance().getPermissions() == UserPermissions.LIBRARIAN){
			btnList.add(new JButton("SHOW USER"));
			btnList.add(new JButton("LIST USERS"));
		}
		if(UsuarioDAO.getInstance().getPermissions() == UserPermissions.ADMINISTRATOR) { 
			btnList.add(new JButton("CREATE LIBRARIAN"));
			btnList.add(new JButton("DELETE LIBRARIAN"));
		}
		
		return btnList;
	}

	@Override
	public List<Event> getButtonEventList() {
		List<Event> eventList = new ArrayList<>();
		
		eventList.add(Event.BAJA_USUARIO);
		eventList.add(Event.SHOW_MODIFICAR_USUARIO);
		if(UsuarioDAO.getInstance().getPermissions() == UserPermissions.ADMINISTRATOR || UsuarioDAO.getInstance().getPermissions() == UserPermissions.LIBRARIAN){
			eventList.add(Event.MOSTRAR_USUARIO);
			eventList.add(Event.LISTAR_USUARIOS);
		}
		if(UsuarioDAO.getInstance().getPermissions() == UserPermissions.ADMINISTRATOR) {
			eventList.add(Event.SHOW_ALTA_USUARIO);
			eventList.add(Event.BAJA_USUARIO);
		}
		
		return eventList;
	}

	@Override
	public String getDni() {
		return dni_key;
	}
}
