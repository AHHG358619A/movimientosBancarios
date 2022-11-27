package co.neoris.movimientosBancarios.service.impl;

import co.neoris.movimientosBancarios.dto.ClientesDTO;
import co.neoris.movimientosBancarios.entity.Clientes;
import co.neoris.movimientosBancarios.repository.ClientesRepository;
import co.neoris.movimientosBancarios.service.ClientesService;
import co.neoris.movimientosBancarios.util.MD5Util;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service("ClientesService")
@Lazy
public class ClientesServiceImpl implements ClientesService {

  @Autowired
  ClientesRepository clientesRepository;

  @Override
  public List<ClientesDTO> getClients() {
    ModelMapper modelMapper = new ModelMapper();
    return clientesRepository.findAll().stream()
        .map(cliente -> modelMapper.map(cliente, ClientesDTO.class)).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = false)
  public ClientesDTO createClient(ClientesDTO dto) {
    ModelMapper modelMapper = new ModelMapper();
    String contraseniaMD5 = MD5Util.getMd5(dto.getContrasenia());
    dto.setContrasenia(contraseniaMD5);
    Clientes clienteNuevo = modelMapper.map(dto, Clientes.class);
    clienteNuevo = clientesRepository.save(clienteNuevo);
    return modelMapper.map(clienteNuevo, ClientesDTO.class);
  }

  @Override
  @Transactional(readOnly = false)
  public boolean deleteClient(int clienteId) {
    Optional<Clientes> clientesOptional = clientesRepository.findById(clienteId);
    if (clientesOptional.isPresent()) {
      clientesRepository.delete(clientesOptional.get());
      return true;
    }

    return false;
  }

  @Override
  @Transactional(readOnly = false)
  public ClientesDTO updateClient(int clienteId, ClientesDTO dtoActualizar) {
    ModelMapper modelMapper = new ModelMapper();

    Optional<Clientes> clientesOptional = clientesRepository.findById(clienteId);

    if (clientesOptional.isPresent()) {
      Clientes cliente = clientesOptional.get();
      cliente.setIdentificacion(dtoActualizar.getIdentificacion());
      cliente.setNombres(dtoActualizar.getNombres());
      cliente.setGenero(dtoActualizar.getGenero());
      cliente.setEdad(dtoActualizar.getEdad());
      cliente.setDireccion(dtoActualizar.getDireccion());
      cliente.setTelefono(dtoActualizar.getTelefono());
      cliente.setContrasenia(MD5Util.getMd5(dtoActualizar.getContrasenia()));
      cliente.setEstado(dtoActualizar.getEstado());

      cliente = clientesRepository.save(cliente);
      return modelMapper.map(cliente, ClientesDTO.class);
    }

    return null;
  }
}
