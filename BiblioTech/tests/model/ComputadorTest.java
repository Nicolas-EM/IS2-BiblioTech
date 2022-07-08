package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

import control.controller.Controller;
import control.events.Event;
import gui.utils.Pair;
import model.dao.items.Computador;
import model.transfers.TransferComputador;

public class ComputadorTest {
	@Test
	void alta() {
		TransferComputador comp = new TransferComputador("", "", 0);//String marca, String modelo, int cantidad
		assertFalse(Controller.getInstance().action(Event.ALTA_COMPUTADOR, comp));
		
		comp = new TransferComputador("Marca","",0);
		assertFalse(Controller.getInstance().action(Event.ALTA_COMPUTADOR, comp));
		
		comp = new TransferComputador("", "GtxPc", 0);
		assertFalse(Controller.getInstance().action(Event.ALTA_COMPUTADOR, comp));
		
		comp = new TransferComputador("", "", 9);
		assertFalse(Controller.getInstance().action(Event.ALTA_COMPUTADOR, comp));
		
		comp = new TransferComputador("Marca", "GtxPc", 0);
		assertFalse(Controller.getInstance().action(Event.ALTA_COMPUTADOR, comp));
		
		comp = new TransferComputador("Marca", "", 30);
		assertFalse(Controller.getInstance().action(Event.ALTA_COMPUTADOR, comp));
		
		comp = new TransferComputador("", "GtxPc", 30);
		assertFalse(Controller.getInstance().action(Event.ALTA_COMPUTADOR, comp));
		
		comp = new TransferComputador("Panel", "Panel", 30);
		assertFalse(Controller.getInstance().action(Event.ALTA_COMPUTADOR, comp));
		
		comp = new TransferComputador("Marca" +(new Random()).nextInt(50), "Modelo" +(new Random()).nextInt(50), 10);
		assertTrue(Controller.getInstance().action(Event.ALTA_COMPUTADOR, comp));
		

		
	}
	
	@Test
	void baja() {
		Computador lDao = new Computador();
		TransferComputador comp = new TransferComputador("DELL", "AAA", 1);
		assertTrue(Controller.getInstance().action(Event.ALTA_COMPUTADOR, comp));
		
		Pair<Computador, TransferComputador> p = new Pair<Computador, TransferComputador>(lDao, comp);
		//baja if entity not exists
		assertFalse(Controller.getInstance().action(Event.BAJA_COMPUTADOR, p));
		
		comp = lDao.get("AAA");
		p.set(lDao, comp);
		//baja if entity exists
		assertTrue(Controller.getInstance().action(Event.BAJA_COMPUTADOR, p));
	}
	
	@Test
	void modificar() {
		TransferComputador comp = new TransferComputador("ASUS", "INTEL CORE i19", 1);
		assertTrue(Controller.getInstance().action(Event.ALTA_COMPUTADOR, comp));
		
		//modificar comp que no existe
		comp = new TransferComputador("ASUS", "INTEL CORE i20", 3);
		assertFalse(Controller.getInstance().action(Event.MODIFICAR_COMPUTADOR, comp));
		Computador lDao = new Computador();
		comp = lDao.get("INTEL CORE i19");
		//modificar comp que si existe
		TransferComputador compMod = new TransferComputador(comp.getId(), "New make", "New Model", comp.getCantidad() + 100);
		assertTrue(Controller.getInstance().action(Event.MODIFICAR_COMPUTADOR, compMod));
	}
}
