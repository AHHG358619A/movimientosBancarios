package co.neoris.movimientosBancarios.service;

import co.neoris.movimientosBancarios.dto.ClientesDTO;
import java.util.List;

public interface ClientesService {

  List<ClientesDTO> getClientes();

  ClientesDTO createCliente(ClientesDTO dto);

}
