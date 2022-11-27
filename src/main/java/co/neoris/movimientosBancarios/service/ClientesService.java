package co.neoris.movimientosBancarios.service;

import co.neoris.movimientosBancarios.dto.ClientesDTO;
import java.util.List;

public interface ClientesService {

  List<ClientesDTO> getClients();

  ClientesDTO createClient(ClientesDTO dto);

  boolean deleteClient(int clienteId);

  ClientesDTO updateClient(int clienteId, ClientesDTO dtoActualizar);
}
