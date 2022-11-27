package co.neoris.movimientosBancarios.service.impl;

import co.neoris.movimientosBancarios.dto.ClientesDTO;
import co.neoris.movimientosBancarios.entity.Clientes;
import co.neoris.movimientosBancarios.repository.ClientesRepository;
import co.neoris.movimientosBancarios.service.ClientesService;
import co.neoris.movimientosBancarios.util.MD5Util;
import java.util.List;
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
  public List<ClientesDTO> getClientes() {
    ModelMapper modelMapper = new ModelMapper();
    return clientesRepository.findAll().stream()
        .map(cliente -> modelMapper.map(cliente, ClientesDTO.class)).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = false)
  public ClientesDTO createCliente(ClientesDTO dto) {
    ModelMapper modelMapper = new ModelMapper();
    String contraseniaMD5 = MD5Util.getMd5(dto.getContrasenia());
    dto.setContrasenia(contraseniaMD5);
    Clientes clienteNuevo = modelMapper.map(dto, Clientes.class);
    clientesRepository.save(clienteNuevo);
    return dto;
  }
}
