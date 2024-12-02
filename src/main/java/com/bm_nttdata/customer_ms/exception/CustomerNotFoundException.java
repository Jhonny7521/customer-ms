package com.bm_nttdata.customer_ms.exception;

/**
 * Excepción personalizada que se lanza cuando no se encuentra un cliente en el sistema.
 * Esta excepción extiende de RuntimeException para manejar casos donde un cliente
 * solicitado no existe en la base de datos.
 */
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
