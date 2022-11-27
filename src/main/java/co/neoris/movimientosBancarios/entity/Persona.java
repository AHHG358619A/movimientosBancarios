package co.neoris.movimientosBancarios.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Persona {

  @Column(length = 250)
  private String nombres;

  @Column(length = 20)
  private String genero;

  @Column(length = 50)
  private String identificacion;

  @Column
  private Integer edad;

  @Column(length = 100)
  private String direccion;

  @Column
  private Integer telefono;
}
