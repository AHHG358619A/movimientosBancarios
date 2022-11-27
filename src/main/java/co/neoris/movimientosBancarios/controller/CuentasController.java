package co.neoris.movimientosBancarios.controller;

import co.neoris.movimientosBancarios.dto.CuentasDTO;
import co.neoris.movimientosBancarios.service.CuentasService;
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
@RequestMapping(ApplicationConstants.API_VERSION + "/cuentas")
public class CuentasController {

  @Autowired
  private CuentasService cuentasService;

  @GetMapping(
      value = "",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(
      value = "Obtener cuentas",
      notes = "Obtener listado de cuentas.")
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public ResponseEntity<List<CuentasDTO>> getCuentas() {

    List<CuentasDTO> cuentasDTOList = cuentasService.getAccounts();

    return new ResponseEntity<>(cuentasDTOList, HttpStatus.OK);
  }

  @ApiOperation(
      value = "Generar cuenta",
      notes = "Generar una nueva cuenta.")
  @PostMapping
  public ResponseEntity<CuentasDTO> createCuenta(@RequestBody CuentasDTO dto)
      throws NotFoundException {

    CuentasDTO cuentasDTO = cuentasService.createAccount(dto);

    if (cuentasDTO != null) {
      return new ResponseEntity(cuentasDTO, HttpStatus.CREATED);
    }

    return new ResponseEntity(null, HttpStatus.NOT_MODIFIED);
  }

  @ApiOperation(
      value = "Borrar cuenta",
      notes = "Borrar cuenta por id.")
  @DeleteMapping(value = "/{cuentaId}")
  public ResponseEntity<Boolean> deleteCuenta(@PathVariable int cuentaId) {

    if (cuentasService.deleteAccount(cuentaId)) {
      return new ResponseEntity(null, HttpStatus.ACCEPTED);
    } else {
      return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(
      value = "Actualizar cuenta",
      notes = "Actualizar cuenta por id.")
  @PutMapping(value = "/{cuentaId}")
  public ResponseEntity<Boolean> deleteCuenta(@PathVariable int cuentaId,
      @RequestBody CuentasDTO dtoActualizar) throws NotFoundException {

    CuentasDTO cuentasDTO = cuentasService.updateAccount(cuentaId, dtoActualizar);

    if (cuentasDTO != null) {
      return new ResponseEntity(null, HttpStatus.ACCEPTED);
    } else {
      return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
  }

}
