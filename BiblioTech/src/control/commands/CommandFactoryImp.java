package control.commands;

import control.commands.entities.*;
import control.commands.entities.Computador.*;
import control.commands.entities.Libro.*;
import control.commands.entities.Sala.*;
import control.commands.gui.*;
import control.events.Event;

public class CommandFactoryImp extends CommandFactory {
	private static CommandInterface commands[] = {
		new ShowAuthCommand(),
		new ShowMainPanelCommand(),
		new ShowAltaUsuarioCommand(),
		new ShowMainItemPanelCommand(),
		new ShowMainUserPanelCommand(),
		new ShowLibrosPrestamosCommand(),
		new ShowError(),
		new ShowMessage(),
		
		new AuthUsuario(),
		new AltaUsuario(),
		new BajaUsuario(),
		new ModificarUsuario(),
		new ShowUsuario(),
		new ShowUsuarioList(),
		new ShowModificarUsuario(),
		new ShowAlta(),//alta cualquier entidad
		new ShowSearchForModify(),//modificar cualquier entidad
		new ShowSearchForDelete(),//borrar cualquier entidad
		new ShowSearchForReserva(),
		new ShowSearchForCancelReserva(),
		new ShowReserva(),
		new ShowCancelReserva(),
		new ShowModificar(),
		//Libros
		new AltaLibro(),
		new SearchLibro(),
		new ModificarLibro(),
		new BajaLibro(),
		new PrestarLibro(),
		new RegresarLibro(),
		new ShowLibro (),
		new ShowListaLibros (),
		//Salas
		new AltaSala(),
		new BajaSala(),
		new SearchSala(),
		new ModificarSala(),
		new ReservaSala(),
		new DelReservaSala(),
		new Show_Sala(),
		new Show_ListaSalas(),
		//Computadores
		new AltaComputador(),
		new BajaComputador(),
		new ModificarComputador(),
		new SearchComputador(),
		new ReservaComputador(),
		new DelReservaComputador(),
		new Show_Computador(),
		new Show_ListaPC()
	};

	@Override
	public CommandInterface getCommand(Event e) {
		for(CommandInterface c : commands) {
			if(c.getId() == e) {
				return c;
			}
		}
		return null;
	}
}
