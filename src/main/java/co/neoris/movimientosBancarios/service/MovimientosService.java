package co.neoris.movimientosBancarios.service;

import co.neoris.movimientosBancarios.dto.MovimientosDTO;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface MovimientosService {

  List<MovimientosDTO> getMovements();

  MovimientosDTO createMovement(MovimientosDTO dto) throws NotFoundException;
  
  boolean deleteMovement(int movimientoId);

  MovimientosDTO updateMovement(int movimientoId, MovimientosDTO dtoActualizar)
      throws NotFoundException;
}
