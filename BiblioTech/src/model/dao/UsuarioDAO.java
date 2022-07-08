package model.dao;

import java.util.List;

import javax.swing.JButton;

import control.UserPermissions;
import control.events.Event;
import model.transfers.TransferUsuario;

public abstract class UsuarioDAO {
	protected static UsuarioDAO instance;
	protected static UserPermissions permissions = UserPermissions.USER;
	protected String dni_key;
	
	public static UsuarioDAO getInstance() {
		if (instance == null) instance = new UsuarioDAOImp();
		return instance;
	}
	
	public UserPermissions getPermissions() {
		return UsuarioDAO.permissions;
	}
	
	public abstract TransferUsuario getUsuario(String dni_key);
	
	public abstract boolean authenticate(TransferUsuario user);
	
	public abstract boolean alta(TransferUsuario user);
	
	public abstract boolean baja(TransferUsuario user);
	
	public abstract boolean modificar(TransferUsuario user);
	
	public abstract void mostrar();
	
	public abstract List<String> getAllDNI();

	public abstract List<JButton> getButtonList();
	
	public abstract List<Event> getButtonEventList();
	
	public abstract String getDni();
	
	public void logout() {
		UsuarioDAO.permissions = UserPermissions.USER;
		UsuarioDAO.instance = null;
	}
}
