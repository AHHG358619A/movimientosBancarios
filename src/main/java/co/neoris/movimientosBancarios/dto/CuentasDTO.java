package co.neoris.movimientosBancarios.dto;

import lombok.Data;

@Data
public class CuentasDTO {

  private Integer id;
  private Integer numeroCuenta;
  private Integer saldoInicial;
  private Boolean estado;
  private Integer clienteId;
  private String nombreCliente;
  private Integer tipoCuentaId;
  private String tipoCuenta;

}
