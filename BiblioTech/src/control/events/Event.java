package control.events;

public enum Event{
	SHOW_AUTHENTICATION, SHOW_MAINSCREEN, SHOW_REGISTRATION, SHOW_ALTA_USUARIO, SHOW_MODIFICAR_USUARIO,
	UPDATE_GUI_MRP_ITEM, UPDATE_GUI_MRP_USER, UPDATE_GUI_RIGHT_PANEL,
	ERROR, MESSAGE, WARNING,
	
	// Usuarios
	AUTH_USER, ALTA_USUARIO, BAJA_USUARIO, MODIFICAR_USUARIO, 
	ALTA_BIBLIOTECARIO, BAJA_BIBLIOTECARIO, MOSTRAR_USUARIO,
	LISTAR_USUARIOS,
	
	SHOW_ALTA,
	SHOW_SEARCH_FOR_MODIFY,
	SHOW_SEARCH_FOR_DELETE,
	SHOW_SEARCH_FOR_RESERVATION,
	SHOW_SEARCH_FOR_CANCEL_RESERVATION,
	SHOW_MODIFICAR,
	SHOW_RESERVA,
	SHOW_CANCEL_RESERVA,
	// Libros
	PRESTAR, REGRESAR, 
	//CREAR_RESERVA_LIBRO, DEL_RESERVA_LIBRO,
//	MULTAS,
//		PAGAR_MULTA,
	ALTA_LIBRO, BUSCAR_LIBRO, MODIFICAR_LIBRO, BAJA_LIBRO,MOSTRAR_LIBRO,LISTAR_LIBROS,
	
	// Ordenadores
	CREAR_RESERVA_COMPUTADOR, DEL_RESERVA_COMPUTADOR, ALTA_COMPUTADOR, BUSCAR_COMPUTADOR, MODIFICAR_COMPUTADOR, BAJA_COMPUTADOR,MOSTRAR_PC,LISTAR_PCS,
	
	// Salas
	CREAR_RESERVA_SALA, DEL_RESERVA_SALA, ALTA_SALA, BUSCAR_SALA, MODIFICAR_SALA, BAJA_SALA,MOSTRAR_SALA,LISTAR_SALAS;
}