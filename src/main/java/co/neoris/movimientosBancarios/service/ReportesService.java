package co.neoris.movimientosBancarios.service;

import co.neoris.movimientosBancarios.dto.ReportesDTO;
import java.util.List;

public interface ReportesService {

  List<ReportesDTO> getReports(String fechaInicio, String fechaFin);
}
