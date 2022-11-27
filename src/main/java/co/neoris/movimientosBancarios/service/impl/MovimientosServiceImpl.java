package co.neoris.movimientosBancarios.service.impl;

import co.neoris.movimientosBancarios.dto.MovimientosDTO;
import co.neoris.movimientosBancarios.entity.Cuentas;
import co.neoris.movimientosBancarios.entity.Movimientos;
import co.neoris.movimientosBancarios.entity.TiposMovimientos;
import co.neoris.movimientosBancarios.repository.CuentasRepository;
import co.neoris.movimientosBancarios.repository.MovimientosRepository;
import co.neoris.movimientosBancarios.repository.TiposMovimientosRepository;
import co.neoris.movimientosBancarios.service.MovimientosService;
import co.neoris.movimientosBancarios.util.exception.ValidationException;
import java.time.LocalDate;
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
@Service("MovimientosService")
@Lazy
public class MovimientosServiceImpl implements MovimientosService {

  @Autowired
  MovimientosRepository movimientosRepository;

  @Autowired
  TiposMovimientosRepository tiposMovimientosRepository;

  @Autowired
  CuentasRepository cuentasRepository;

  PropertyMap<Movimientos, MovimientosDTO> movimientosMap = new PropertyMap<Movimientos, MovimientosDTO>() {
    protected void configure() {
      map().setTipoMovimientoId(source.getTipoMovimiento().getId());
      map().setTipoMovimiento(source.getTipoMovimiento().getTipo());
      map().setTipoCuentaId(source.getCuenta().getTipoCuenta().getId());
      map().setTipoCuenta(source.getCuenta().getTipoCuenta().getTipo());
      map().setCuentaId(source.getCuenta().getId());
      map().setNumeroCuenta(source.getCuenta().getNumeroCuenta());
    }
  };

  @Override
  public List<MovimientosDTO> getMovements() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(movimientosMap);
    return movimientosRepository.findAll().stream()
        .map(movimiento -> modelMapper.map(movimiento, MovimientosDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = false)
  public MovimientosDTO createMovement(MovimientosDTO dto) throws NotFoundException {

    Cuentas cuenta = cuentasRepository.findById(dto.getCuentaId())
        .orElseThrow(NotFoundException::new);

    TiposMovimientos tipoMovimiento = tiposMovimientosRepository.findById(dto.getTipoMovimientoId())
        .orElseThrow(NotFoundException::new);

    if (dto.getTipoMovimientoId() == 1 && dto.getValor() < 0) {
      throw new ValidationException(("El valor del crédito debe ser positivo"));
    }

    if (dto.getTipoMovimientoId() == 2 && dto.getValor() > 0) {
      throw new ValidationException(("El valor del débito debe ser negativo"));
    }

    int saldoCuenta = cuenta.getSaldo();

    if (dto.getTipoMovimientoId() == 2) {
      if (saldoCuenta == 0 || saldoCuenta + dto.getValor() < 0) {
        throw new ValidationException(("Saldo no disponible"));
      }
    }

    int nuevoSaldoDisponible = saldoCuenta + dto.getValor();

    Movimientos movimiento = new Movimientos();
    movimiento.setFecha(LocalDate.now());
    movimiento.setTipoMovimiento(tipoMovimiento);
    movimiento.setValor(dto.getValor());
    movimiento.setSaldoInicial(saldoCuenta);
    movimiento.setSaldoDisponible(nuevoSaldoDisponible);
    movimiento.setEstado(dto.getEstado());
    movimiento.setCuenta(cuenta);

    movimiento = movimientosRepository.save(movimiento);

    cuenta.setSaldo(nuevoSaldoDisponible);
    cuentasRepository.save(cuenta);

    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(movimientosMap);

    return modelMapper.map(movimiento, MovimientosDTO.class);
  }

  @Override
  @Transactional(readOnly = false)
  public boolean deleteMovement(int movimientoId) {
    Optional<Movimientos> movimientosOptional = movimientosRepository.findById(movimientoId);
    if (movimientosOptional.isPresent()) {
      movimientosRepository.delete(movimientosOptional.get());
      return true;
    }

    return false;
  }

  @Override
  @Transactional(readOnly = false)
  public MovimientosDTO updateMovement(int movimientoId, MovimientosDTO dtoActualizar)
      throws NotFoundException {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(movimientosMap);

    Cuentas cuenta = cuentasRepository.findById(dtoActualizar.getCuentaId())
        .orElseThrow(NotFoundException::new);

    TiposMovimientos tipoMovimiento = tiposMovimientosRepository.findById(
        dtoActualizar.getTipoMovimientoId()).orElseThrow(NotFoundException::new);

    Optional<Movimientos> movimientosOptional = movimientosRepository.findById(movimientoId);

    if (movimientosOptional.isPresent()) {
      Movimientos movimiento = movimientosOptional.get();

      movimiento.setFecha(dtoActualizar.getFecha());
      movimiento.setTipoMovimiento(tipoMovimiento);
      movimiento.setValor(dtoActualizar.getValor());
      movimiento.setSaldoInicial(dtoActualizar.getSaldoInicial());
      movimiento.setSaldoDisponible(dtoActualizar.getSaldoDisponible());
      movimiento.setEstado(dtoActualizar.getEstado());
      movimiento.setCuenta(cuenta);

      movimiento = movimientosRepository.save(movimiento);
      return modelMapper.map(movimiento, MovimientosDTO.class);
    }

    return null;
  }
}
