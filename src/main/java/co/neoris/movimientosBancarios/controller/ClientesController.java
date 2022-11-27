package co.neoris.movimientosBancarios.controller;

import co.neoris.movimientosBancarios.dto.ClientesDTO;
import co.neoris.movimientosBancarios.service.ClientesService;
import co.neoris.movimientosBancarios.util.ApplicationConstants;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApplicationConstants.API_VERSION + "/clientes")
public class ClientesController {

  @Autowired
  private ClientesService clientesService;

  @GetMapping(
      value = "",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(
      value = "Obtener clientes",
      notes = "Obtener listado de clientes.")
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public ResponseEntity<List<ClientesDTO>> getClientes() {

    List<ClientesDTO> clientesDTOList = clientesService.getClientes();

    return new ResponseEntity<>(clientesDTOList, HttpStatus.OK);
  }

  @ApiOperation(
      value = "Generar cliente",
      notes = "Generar un nuevo cliente.")
  @PostMapping
  public ResponseEntity<ClientesDTO> createCliente(@RequestBody ClientesDTO dto) {

    ClientesDTO clientesDTO = clientesService.createCliente(dto);

    if (clientesDTO != null) {
      return new ResponseEntity(clientesDTO, HttpStatus.CREATED);
    }

    return new ResponseEntity(null, HttpStatus.NOT_MODIFIED);
  }

}
