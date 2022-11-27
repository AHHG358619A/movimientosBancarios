package co.neoris.movimientosBancarios.service;

import co.neoris.movimientosBancarios.dto.CuentasDTO;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface CuentasService {

  List<CuentasDTO> getAccounts();

  CuentasDTO createAccount(CuentasDTO dto) throws NotFoundException;

  boolean deleteAccount(int cuentaId);

  CuentasDTO updateAccount(int cuentaId, CuentasDTO dtoActualizar) throws NotFoundException;
}
