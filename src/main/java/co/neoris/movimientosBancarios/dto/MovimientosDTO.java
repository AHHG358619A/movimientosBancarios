package co.neoris.movimientosBancarios.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MovimientosDTO {

  private Integer id;
  private LocalDate fecha;
  private Integer valor;
  private Integer saldoInicial;
  private Integer saldoDisponible;
  private Boolean estado;
  private Integer tipoMovimientoId;
  private String tipoMovimiento;
  private Integer tipoCuentaId;
  private String tipoCuenta;
  private Integer cuentaId;
  private Integer numeroCuenta;
}
