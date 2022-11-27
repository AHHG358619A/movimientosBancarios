package co.neoris.movimientosBancarios.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Clientes extends Persona {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 32)
  private String contrasenia;

  @Column
  private Boolean estado;

  @OneToMany(mappedBy = "cliente")
  private Set<Cuentas> clienteCuentas;

}
