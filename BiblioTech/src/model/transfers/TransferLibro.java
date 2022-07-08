package model.transfers;

import java.util.ArrayList;
import java.util.List;

public class TransferLibro extends Transfer {
	private int ISBN;
	private String Titulo;
	private int Cantidad;
	
	public TransferLibro(int isbn, String titulo, int cantidad) {
		super(-1);
		ISBN = isbn;
		Titulo = titulo;
		Cantidad = cantidad;
	}
	public TransferLibro(int id, int isbn, String titulo, int cantidad) {
		super(id);
		ISBN = isbn;
		Titulo = titulo;
		Cantidad = cantidad;
	}
	
	
	/*public int getId() {
		return id;
	}*/
	public int getISBN() {
		return ISBN;
	}
	public String getTitulo() {
		return Titulo;
	}
	
	public int getCantidad() {
		return Cantidad;
	}
	@Override
	public List<String> toList() {
		List<String> data = new ArrayList<String>();
		//data.add(Integer.toString(getId()));
		data.add(Integer.toString(ISBN));
		data.add(Titulo);
		data.add(Integer.toString(Cantidad));
		return data;
	}
}
