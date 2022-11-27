package co.neoris.movimientosBancarios.dto;

import lombok.Data;

@Data
public class ClientesDTO {

  private Integer id;
  private String nombres;
  private String genero;
  private String identificacion;
  private Integer edad;
  private String direccion;
  private Integer telefono;
  private String contrasenia;
  private Boolean estado;

}
