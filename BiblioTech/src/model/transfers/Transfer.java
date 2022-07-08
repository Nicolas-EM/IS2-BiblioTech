package model.transfers;

import java.util.List;

public abstract class Transfer {
	private int id;
	
	public Transfer(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public abstract List<String> toList(); 
}
