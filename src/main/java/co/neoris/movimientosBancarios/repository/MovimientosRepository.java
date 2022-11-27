package co.neoris.movimientosBancarios.repository;

import co.neoris.movimientosBancarios.entity.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientosRepository extends JpaRepository<Movimientos, Integer> {

}
