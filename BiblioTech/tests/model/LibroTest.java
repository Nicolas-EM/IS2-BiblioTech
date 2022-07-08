package model;

import org.junit.jupiter.api.Test;

import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Libro;
import model.transfers.TransferLibro;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibroTest {
	@Test
	void alta() {
		//vacio
		TransferLibro libro = new TransferLibro(0, "", 0);//int isbn, String titulo, int cantidad
		assertFalse(Controller.getInstance().action(Event.ALTA_LIBRO, libro));
		
		//Valid data (11)
		libro = new TransferLibro(92356790, "El Cid Campero", 1);
		assertTrue(Controller.getInstance().action(Event.ALTA_LIBRO, libro));
	}
	
	@Test
	void baja() {
		Libro lDao = new Libro();
		TransferLibro libro = new TransferLibro(232, "EA", 1);
		assertTrue(Controller.getInstance().action(Event.ALTA_LIBRO, libro));
		
		Pair<Libro, TransferLibro> p = new Pair<Libro, TransferLibro>(lDao, libro);
		//baja if entity not exists
		assertFalse(Controller.getInstance().action(Event.BAJA_LIBRO, p));
		
		libro = lDao.get("232");
		p.set(lDao, libro);
		//baja if entity exists
		assertTrue(Controller.getInstance().action(Event.BAJA_LIBRO, p));
	}
	
	@Test
	void modificar() {
		TransferLibro libro = new TransferLibro(2334, "El Cid Campero", 1);
		assertTrue(Controller.getInstance().action(Event.ALTA_LIBRO, libro));
		
		//modificar libro que no existe
		libro = new TransferLibro(321, "AAAA", 3);
		assertFalse(Controller.getInstance().action(Event.MODIFICAR_LIBRO, libro));
		Libro lDao = new Libro();
		libro = lDao.get("2334");
		//modificar libro que si existe
		TransferLibro libroMod = new TransferLibro(libro.getId(), libro.getISBN(), "New title", libro.getCantidad() + 100);
		assertTrue(Controller.getInstance().action(Event.MODIFICAR_LIBRO, libroMod));
	}
}
