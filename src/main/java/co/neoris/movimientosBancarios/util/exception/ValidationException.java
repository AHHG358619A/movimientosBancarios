package co.neoris.movimientosBancarios.util.exception;

public class ValidationException extends RuntimeException {

  public ValidationException() {
    super("Error de validación");
  }

  public ValidationException(String msg) {
    super(msg);
  }
}
