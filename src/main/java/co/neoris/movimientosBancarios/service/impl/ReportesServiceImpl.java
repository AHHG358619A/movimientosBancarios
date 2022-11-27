package co.neoris.movimientosBancarios.service.impl;

import co.neoris.movimientosBancarios.dto.ReportesDTO;
import co.neoris.movimientosBancarios.entity.Movimientos;
import co.neoris.movimientosBancarios.repository.MovimientosRepository;
import co.neoris.movimientosBancarios.service.ReportesService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service("ReportesService")
@Lazy
public class ReportesServiceImpl implements ReportesService {

  @Autowired
  MovimientosRepository movimientosRepository;

  PropertyMap<Movimientos, ReportesDTO> reportesMap = new PropertyMap<Movimientos, ReportesDTO>() {
    protected void configure() {
      map().setCliente(source.getCuenta().getCliente().getNombres());
      map().setNumeroCuenta(source.getCuenta().getNumeroCuenta());
      map().setTipoCuenta(source.getCuenta().getTipoCuenta().getTipo());
      map().setMovimiento(source.getValor());
    }
  };

  @Override
  public List<ReportesDTO> getReports() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(reportesMap);
    return movimientosRepository.findAll().stream()
        .map(movimiento -> modelMapper.map(movimiento, ReportesDTO.class))
        .collect(Collectors.toList());
  }
}
