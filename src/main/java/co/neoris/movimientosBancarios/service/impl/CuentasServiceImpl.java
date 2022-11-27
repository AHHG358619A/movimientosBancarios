package co.neoris.movimientosBancarios.service.impl;

import co.neoris.movimientosBancarios.dto.CuentasDTO;
import co.neoris.movimientosBancarios.entity.Clientes;
import co.neoris.movimientosBancarios.entity.Cuentas;
import co.neoris.movimientosBancarios.entity.TiposCuentas;
import co.neoris.movimientosBancarios.repository.ClientesRepository;
import co.neoris.movimientosBancarios.repository.CuentasRepository;
import co.neoris.movimientosBancarios.repository.TiposCuentasRepository;
import co.neoris.movimientosBancarios.service.CuentasService;
import co.neoris.movimientosBancarios.util.exception.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service("CuentasService")
@Lazy
public class CuentasServiceImpl implements CuentasService {

  @Autowired
  CuentasRepository cuentasRepository;

  @Autowired
  ClientesRepository clientesRepository;

  @Autowired
  TiposCuentasRepository tiposCuentasRepository;

  PropertyMap<Cuentas, CuentasDTO> cuentasMap = new PropertyMap<Cuentas, CuentasDTO>() {
    protected void configure() {
      map().setClienteId(source.getCliente().getId());
      map().setNombreCliente(source.getCliente().getNombres());
      map().setTipoCuentaId(source.getTipoCuenta().getId());
      map().setTipoCuenta(source.getTipoCuenta().getTipo());
    }
  };

  @Override
  public List<CuentasDTO> getAccounts() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(cuentasMap);
    return cuentasRepository.findAll().stream()
        .map(cuenta -> modelMapper.map(cuenta, CuentasDTO.class)).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = false)
  public CuentasDTO createAccount(CuentasDTO dto) throws NotFoundException {

    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(cuentasMap);

    if (dto.getSaldoInicial() < 0) {
      throw new ValidationException(("Saldo inicial debe ser mayor o igual a cero"));
    }

    Clientes cliente = clientesRepository.findById(dto.getClienteId())
        .orElseThrow(NotFoundException::new);

    TiposCuentas tipoCuenta = tiposCuentasRepository.findById(dto.getTipoCuentaId())
        .orElseThrow(NotFoundException::new);

    Cuentas cuenta = new Cuentas();

    cuenta.setTipoCuenta(tipoCuenta);
    cuenta.setCliente(cliente);
    cuenta.setNumeroCuenta(dto.getNumeroCuenta());
    cuenta.setEstado(dto.getEstado());
    cuenta.setSaldoInicial(dto.getSaldoInicial());

    cuenta = cuentasRepository.save(cuenta);

    return modelMapper.map(cuenta, CuentasDTO.class);

  }

  @Override
  @Transactional(readOnly = false)
  public boolean deleteAccount(int cuentaId) {
    Optional<Cuentas> cuentaOptional = cuentasRepository.findById(cuentaId);
    if (cuentaOptional.isPresent()) {
      cuentasRepository.delete(cuentaOptional.get());
      return true;
    }

    return false;
  }

  @Override
  @Transactional(readOnly = false)
  public CuentasDTO updateAccount(int cuentaId, CuentasDTO dtoActualizar) throws NotFoundException {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(cuentasMap);

    Optional<Cuentas> cuentaOptional = cuentasRepository.findById(cuentaId);

    if (cuentaOptional.isPresent()) {
      Cuentas cuenta = cuentaOptional.get();
      Clientes cliente = clientesRepository.findById(dtoActualizar.getClienteId())
          .orElseThrow(NotFoundException::new);

      TiposCuentas tipoCuenta = tiposCuentasRepository.findById(dtoActualizar.getTipoCuentaId())
          .orElseThrow(NotFoundException::new);

      cuenta.setTipoCuenta(tipoCuenta);
      cuenta.setCliente(cliente);
      cuenta.setNumeroCuenta(dtoActualizar.getNumeroCuenta());
      cuenta.setEstado(dtoActualizar.getEstado());
      cuenta.setSaldoInicial(dtoActualizar.getSaldoInicial());

      cuenta = cuentasRepository.save(cuenta);
      return modelMapper.map(cuenta, CuentasDTO.class);
    }

    return null;
  }
}
