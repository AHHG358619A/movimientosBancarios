package co.neoris.movimientosBancarios.controller;

import co.neoris.movimientosBancarios.dto.MovimientosDTO;
import co.neoris.movimientosBancarios.service.MovimientosService;
import co.neoris.movimientosBancarios.util.ApplicationConstants;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApplicationConstants.API_VERSION + "/movimientos")
public class MovimientosController {

  @Autowired
  private MovimientosService movimientosService;

  @GetMapping(
      value = "",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(
      value = "Obtener movimientos",
      notes = "Obtener listado de movimientos.")
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public ResponseEntity<List<MovimientosDTO>> getMovimientos() {

    List<MovimientosDTO> movimientosDTOList = movimientosService.getMovements();

    return new ResponseEntity<>(movimientosDTOList, HttpStatus.OK);
  }

  @ApiOperation(
      value = "Generar movimientos",
      notes = "Generar un nuevo movimiento.")
  @PostMapping
  public ResponseEntity<MovimientosDTO> createMovimiento(@RequestBody MovimientosDTO dto)
      throws NotFoundException {

    MovimientosDTO movimientosDTO = movimientosService.createMovement(dto);

    if (movimientosDTO != null) {
      return new ResponseEntity(movimientosDTO, HttpStatus.CREATED);
    }

    return new ResponseEntity(null, HttpStatus.NOT_MODIFIED);
  }

  @ApiOperation(
      value = "Borrar movimiento",
      notes = "Borrar movimiento por id.")
  @DeleteMapping(value = "/{movimientoId}")
  public ResponseEntity<Boolean> deleteMovimiento(@PathVariable int movimientoId) {

    if (movimientosService.deleteMovement(movimientoId)) {
      return new ResponseEntity(null, HttpStatus.ACCEPTED);
    } else {
      return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(
      value = "Actualizar movimiento",
      notes = "Actualizar movimiento por id.")
  @PutMapping(value = "/{movimientoId}")
  public ResponseEntity<Boolean> deleteMovimiento(@PathVariable int movimientoId,
      @RequestBody MovimientosDTO dtoActualizar) throws NotFoundException {

    MovimientosDTO movimientosDTO = movimientosService.updateMovement(movimientoId, dtoActualizar);

    if (movimientosDTO != null) {
      return new ResponseEntity(null, HttpStatus.ACCEPTED);
    } else {
      return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
  }
  
}
