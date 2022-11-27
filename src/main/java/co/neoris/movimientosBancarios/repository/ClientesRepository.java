package co.neoris.movimientosBancarios.repository;

import co.neoris.movimientosBancarios.entity.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository<Clientes, Integer> {

}
