package co.neoris.movimientosBancarios.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ReportesDTO {

  private Integer id;
  private LocalDate fecha;
  private String cliente;
  private Integer numeroCuenta;
  private String tipoCuenta;
  private Integer saldoInicial;
  private Boolean estado;
  private Integer movimiento;
  private Integer saldoDisponible;

}
