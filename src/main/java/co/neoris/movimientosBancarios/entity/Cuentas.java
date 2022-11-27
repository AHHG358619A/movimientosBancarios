package co.neoris.movimientosBancarios.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Cuentas {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private Integer numeroCuenta;

  @Column
  private Integer saldoInicial;

  @Column
  private Boolean estado;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tipo_cuenta_id")
  private TiposCuentas tipoCuenta;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cliente_id")
  private Clientes cliente;

  @OneToMany(mappedBy = "cuenta")
  private Set<Movimientos> cuentaMovimientos;

}
