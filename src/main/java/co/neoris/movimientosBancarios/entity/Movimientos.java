package co.neoris.movimientosBancarios.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Movimientos {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private LocalDate fecha;

  @Column
  private Integer valor;

  @Column
  private Integer saldo;

  @Column
  private Boolean estado;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tipo_movimiento_id")
  private TiposMovimientos tipoMovimiento;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cuenta_id")
  private Cuentas cuenta;

}
