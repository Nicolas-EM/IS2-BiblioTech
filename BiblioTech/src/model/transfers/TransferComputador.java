package model.transfers;

import java.util.ArrayList;
import java.util.List;

public class TransferComputador extends Transfer {

	private String Marca;
	private String Modelo;
	private int Cantidad;
	public TransferComputador(int id, String marca, String modelo, int cantidad) {
		super(id);
		Marca = marca;
		Modelo = modelo;
		Cantidad = cantidad;
	}
	public TransferComputador(String marca, String modelo, int cantidad) {
		super(-1);
		Marca = marca;
		Modelo = modelo;
		Cantidad = cantidad;
	}
	
	public String getMarca() {
		return Marca;
	}
	
	public String getModelo() {
		return Modelo;
	}
	public int getCantidad() {
		return Cantidad;
	}
	@Override
	public List<String> toList() {
		List<String> data = new ArrayList<String>();
		data.add(Marca);
		data.add(Modelo);
		data.add(Integer.toString(Cantidad));
		return data;
	}
}
