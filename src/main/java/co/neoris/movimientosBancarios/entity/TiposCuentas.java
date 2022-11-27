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
public class TiposCuentas {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 50)
  private String tipo;

  @Column(length = 50)
  private String descripcion;

  @OneToMany(mappedBy = "tipoCuenta")
  private Set<Cuentas> tipoCuentaCuentas;

}
