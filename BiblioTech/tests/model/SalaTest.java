package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Sala;
import model.transfers.TransferSala;

public class SalaTest {
	@Test
	void alta() {
		//vacio
		TransferSala sala = new TransferSala("", 0);
		assertFalse(Controller.getInstance().action(Event.ALTA_SALA, sala));
		
		//Valid data
		sala = new TransferSala("SALA GENERICA 1", 1);
		assertTrue(Controller.getInstance().action(Event.ALTA_SALA, sala));
	}
	
	@Test
	void baja() {
		Sala lDao = new Sala();
		TransferSala sala = new TransferSala("SALA", 1);
		assertTrue(Controller.getInstance().action(Event.ALTA_SALA, sala));
		
		Pair<Sala, TransferSala> p = new Pair<Sala, TransferSala>(lDao, sala);
		//baja if entity not exists
		assertFalse(Controller.getInstance().action(Event.BAJA_SALA, p));
		
		sala = lDao.get("SALA");
		p.set(lDao, sala);
		//baja if entity exists
		assertTrue(Controller.getInstance().action(Event.BAJA_SALA, p));
	}
	
	@Test
	void modificar() {
		TransferSala sala = new TransferSala("SALA 1", 1);
		assertTrue(Controller.getInstance().action(Event.ALTA_SALA, sala));
		
		//modificar libro que no existe
		sala = new TransferSala(321, "SALA 2", 3);
		assertFalse(Controller.getInstance().action(Event.MODIFICAR_SALA, sala));
		Sala lDao = new Sala();
		sala = lDao.get("SALA 1");
		//modificar libro que si existe
		TransferSala salaMod = new TransferSala(sala.getId(), "New name", sala.getCantidad() + 100);
		assertTrue(Controller.getInstance().action(Event.MODIFICAR_SALA, salaMod));
	}
	
}
