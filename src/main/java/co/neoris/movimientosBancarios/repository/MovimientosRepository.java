package co.neoris.movimientosBancarios.repository;

import co.neoris.movimientosBancarios.entity.Movimientos;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientosRepository extends JpaRepository<Movimientos, Integer> {

  List<Movimientos> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

}
