package model.transfers;

import java.sql.Date;

import control.UserPermissions;

public class TransferUsuario {
	private int id;
	private String dni;
	private String pwd;
	private String name;
	private String surname;
	private String correo;
	private UserPermissions permissions;
	Date fnacimiento;
	
	// Para baja
	public TransferUsuario(String dni) {
		this.dni = dni;
	}
	
	// Para auth
	public TransferUsuario(String dni, String pwd) {
		this.dni = dni;
		this.pwd = pwd;
	}
	
	// Para modificaciones
	public TransferUsuario(String dni, String name, String surname, String correo) {
		this.dni = dni;
		this.name = name;
		this.surname = surname;
		this.correo = correo;
	}
	
	// Para altas
	public TransferUsuario(String dni, String pwd, String name, String surname, Date fnacimiento, String correo) {
		this.dni = dni;
		this.pwd = pwd;
		this.name = name;
		this.surname = surname;
		this.fnacimiento = fnacimiento;
		this.correo = correo;
	}
	
	// Para altas
	public TransferUsuario(int id, String dni, String pwd, String name, String surname, Date fnacimiento, String correo, UserPermissions permissions) {
		this.id = id;
		this.dni = dni;
		this.pwd = pwd;
		this.name = name;
		this.surname = surname;
		this.fnacimiento = fnacimiento;
		this.correo = correo;
		this.permissions = permissions;
	}

	public int getId() {
		return id;
	}
	public String getDni() {
		return dni;
	}
	public String getPwd() {
		return pwd;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getCorreo() {
		return correo;
	}
	public Date getFnacimiento() {
		return fnacimiento;
	}
	
	public UserPermissions getPermissions() {
		return permissions;
	}
}
