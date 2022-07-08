package model.transfers;

import java.util.ArrayList;
import java.util.List;

public class TransferSala extends Transfer {
	
	public String Nombre;
	public int Cantidad;
	
	public TransferSala(int id, String nombre, int cantidad) {
		super(id);
		Nombre = nombre;
		Cantidad = cantidad;
	}
	public TransferSala(String nombre, int cantidad) {
		super(-1);
		Nombre = nombre;
		Cantidad = cantidad;
	}
	public String getNombre() {
		return Nombre;
	}
	public int getCantidad() {
		return Cantidad;
	}
	@Override
	public List<String> toList() {
		List<String> data = new ArrayList<String>();
		data.add(Nombre);
		data.add(Integer.toString(Cantidad));
		return data;
	}
}
