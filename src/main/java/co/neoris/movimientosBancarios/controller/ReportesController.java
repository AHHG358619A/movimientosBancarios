package co.neoris.movimientosBancarios.controller;

import co.neoris.movimientosBancarios.dto.ReportesDTO;
import co.neoris.movimientosBancarios.service.ReportesService;
import co.neoris.movimientosBancarios.util.ApplicationConstants;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApplicationConstants.API_VERSION + "/reportes")
public class ReportesController {

  @Autowired
  private ReportesService reportesService;

  @GetMapping(
      value = "",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(
      value = "Obtener reportes",
      notes = "Obtener listado de reportes.")
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public ResponseEntity<List<ReportesDTO>> getReportes(
      @RequestParam(required = true, name = "fechaInicio") String fechaInicio,
      @RequestParam(required = true, name = "fechaFin") String fechaFin) {

    List<ReportesDTO> reportesDTOList = reportesService.getReports(fechaInicio, fechaFin);

    return new ResponseEntity<>(reportesDTOList, HttpStatus.OK);
  }

}
