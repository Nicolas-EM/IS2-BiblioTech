package model;

import org.junit.jupiter.api.Test;

import control.controller.Controller;
import control.events.Event;
import model.transfers.TransferUsuario;
import model.utils.FieldValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.Random;

public class UsuarioTest {

	@Test
	void authentication() {
		// Usando constructor
		// TransferUsuario(String dni, String pwd)
		
		// Username and password empty
		TransferUsuario user = new TransferUsuario("", "");
		assertFalse(Controller.getInstance().action(Event.AUTH_USER, user));
		
		// Username empty, random password
		user = new TransferUsuario("", "non-emptyPwd");
		assertFalse(Controller.getInstance().action(Event.AUTH_USER, user));
		
		// Username doesn't exist, empty password
		user = new TransferUsuario("aaaa", "");
		assertFalse(Controller.getInstance().action(Event.AUTH_USER, user));

		// Username exists, empty password
		user = new TransferUsuario("admin", "");
		assertFalse(Controller.getInstance().action(Event.AUTH_USER, user));
		
		// Username too long, empty password
		user = new TransferUsuario("aaaaaaaaaaa", "");
		assertFalse(Controller.getInstance().action(Event.AUTH_USER, user));
		
		// Username too long, random password
		user = new TransferUsuario("aaaaaaaaaaa", "aaa");
		assertFalse(Controller.getInstance().action(Event.AUTH_USER, user));
		
		// Username doesn't exist, password too long
		user = new TransferUsuario("aaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		assertFalse(Controller.getInstance().action(Event.AUTH_USER, user));
		
		// Username exists, password too long
		user = new TransferUsuario("admin", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		assertFalse(Controller.getInstance().action(Event.AUTH_USER, user));
		
		// Username exists, wrong password
		user = new TransferUsuario("admin", "fakePassword");
		assertFalse(Controller.getInstance().action(Event.AUTH_USER, user));
		
		// Username exists, correct password
		user = new TransferUsuario("user", "user");
		assertTrue(Controller.getInstance().action(Event.AUTH_USER, user));
	}

	@Test
	void alta() {
		// Usando constructor
		// TransferUsuario(String dni, String pwd, String name, String surname, Date fnacimiento, String correo)
		
		// Todo vacio
		TransferUsuario user = new TransferUsuario("", "", "", "", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que NO EXISTE
		user = new TransferUsuario("testDNI", "", "", "", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que NO EXISTE y demasiado largo
		user = new TransferUsuario("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "", "", "", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que EXISTE
		user = new TransferUsuario("user", "", "", "", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que NO EXISTE y pwd
		user = new TransferUsuario("user", "password", "", "", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que NO EXISTE y pwd demasiado largo
		user = new TransferUsuario("testDNI", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "", "", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que EXISTE y pwd demasiado largo
		user = new TransferUsuario("user", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "", "", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que NO EXISTE y name
		user = new TransferUsuario("testDNI", "", "name", "", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que NO EXISTE y name demasiado largo
		user = new TransferUsuario("testDNI", "", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que EXISTE y name
		user = new TransferUsuario("user", "", "name", "", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que EXISTE y name demasiado largo
		user = new TransferUsuario("user", "", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que NO EXISTE y surname
		user = new TransferUsuario("testDNI", "", "", "surname", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que NO EXISTE y surname demasiado largo
		user = new TransferUsuario("testDNI", "", "", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que EXISTE y surname
		user = new TransferUsuario("user", "", "", "surname", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que EXISTE y surname demasiado largo
		user = new TransferUsuario("user", "", "", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que NO EXISTE y fecha
		user = new TransferUsuario("testDNI", "", "", "", new Date(0), "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que EXISTE y fecha
		user = new TransferUsuario("user", "", "", "", new Date(0), "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que NO EXISTE y correo NO VALIDO
		user = new TransferUsuario("testDNI", "", "", "", null, "email");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que NO EXISTE y correo VALIDO
		user = new TransferUsuario("testDNI", "", "", "", null, "valid@email.com");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que NO EXISTE y correo demasiado LARGO
		user = new TransferUsuario("testDNI", "", "", "", null, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@email.com");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que EXISTE y correo NO VALIDO
		user = new TransferUsuario("user", "", "", "", null, "email");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que EXISTE y correo VALIDO
		user = new TransferUsuario("user", "", "", "", null, "valid@email.com");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni que EXISTE y correo demasiado LARGO
		user = new TransferUsuario("user", "", "", "", null, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@email.com");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Solo dni NO EXISTE, pwd, y name
		user = new TransferUsuario("user", "", "", "", new java.sql.Date(System.currentTimeMillis() + 86400000), "valid@email.com");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// Todo bien, FECHA EN EL FUTURO
		user = new TransferUsuario("newUser", "a", "a", "a", null, "");
		assertFalse(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// -------------------------------------------------------------------------------------
		// si llegaste a este punto, me rendi. Ya no quiero hacer copy paste de mas combinaciones
		// -------------------------------------------------------------------------------------
		
		
		
		
		// DNI - NO EXISTE y VALIDO (Gracias anonimo por tu DNI)
		// PWD - VALIDO
		// NAME - VALDIO
		// SURNAME - VALIDO
		// DATE - VALIDO
		// CORREO - VALIDO
		
		FieldValidator.desactivar();
		
		user = new TransferUsuario("testDNI" + (new Random()).nextInt(50), "password", "test", "dni", new Date(0), "test@dni.com");
		assertTrue(Controller.getInstance().action(Event.ALTA_USUARIO, user));
	}

	@Test
	void baja() {
		FieldValidator.desactivar();
		
		// DNI - VACIO
		TransferUsuario user = new TransferUsuario("");
		assertFalse(Controller.getInstance().action(Event.BAJA_USUARIO, user));
		
		// DNI - NO EXISTE
		user = new TransferUsuario("testDNI" + Math.random());
		assertFalse(Controller.getInstance().action(Event.BAJA_USUARIO, user));
		
		
		// DNI - VALIDO, baja a si mismo
		// 1. Creamos un usuario
		String username = "testDNI" + (new Random()).nextInt(50);
		user = new TransferUsuario(username, "password", "test", "dni", new Date(0), "test@dni.com");
		assertTrue(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// No deberíamos poder eliminarlo
		user = new TransferUsuario(username);
		assertFalse(Controller.getInstance().action(Event.BAJA_USUARIO, user));
		
		// 2. Autenticamos como USER
		user = new TransferUsuario(username, "password");
		assertTrue(Controller.getInstance().action(Event.AUTH_USER, user));
		
		// 3. Eliminamos
		user = new TransferUsuario(username);
		assertTrue(Controller.getInstance().action(Event.BAJA_USUARIO, user));
		
		// DNI - VALIDO, baja de Admin a otro usuario
		username = "testDNI" + (new Random()).nextInt(50);
		user = new TransferUsuario(username, "password", "test", "dni", new Date(0), "test@dni.com");
		assertTrue(Controller.getInstance().action(Event.ALTA_USUARIO, user));
		
		// 2. Autenticamos como ADMIN
		user = new TransferUsuario("admin", "admin");
		assertTrue(Controller.getInstance().action(Event.AUTH_USER, user));
		
		// 3. Eliminamos
		user = new TransferUsuario(username);
		assertTrue(Controller.getInstance().action(Event.BAJA_USUARIO, user));
	}

	@Test
	void modificar() {
		// Valores a null
		TransferUsuario user = new TransferUsuario("", "", "", "");
		assertFalse(Controller.getInstance().action(Event.MODIFICAR_USUARIO, user));
		
		// dni no existe
		user = new TransferUsuario("testDNI" + (new Random()).nextInt(50), "name", "surname", "email@email.com");
		assertFalse(Controller.getInstance().action(Event.MODIFICAR_USUARIO, user));
		
		// dni EXISTE, name demasiado largo
		user = new TransferUsuario("user", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "surname", "email@email.com");
		assertFalse(Controller.getInstance().action(Event.MODIFICAR_USUARIO, user));
		
		// dni EXISTE, surname demasiado largo
		user = new TransferUsuario("user", "name", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "email@email.com");
		assertFalse(Controller.getInstance().action(Event.MODIFICAR_USUARIO, user));
		
		// dni EXISTE, email INVALIDO
		user = new TransferUsuario("user", "name", "surname", "INVALID_EMAIL");
		assertFalse(Controller.getInstance().action(Event.MODIFICAR_USUARIO, user));
		
		// Valido
		user = new TransferUsuario("user", "new name", "new surname", "new@email.com");
		assertTrue(Controller.getInstance().action(Event.MODIFICAR_USUARIO, user));
	}
}
