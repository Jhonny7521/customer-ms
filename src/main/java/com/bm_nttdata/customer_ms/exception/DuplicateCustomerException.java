package com.bm_nttdata.customer_ms.exception;

/**
 * Excepción personalizada que se lanza cuando el numero de documento ya se encuentra
 * registrado en el sistema.
 * Esta excepción extiende de RuntimeException para manejar casos donde el numero de documento
 * de un cliente ya se encuentra registrado en la base de datos.
 */
public class DuplicateCustomerException extends RuntimeException {

    public DuplicateCustomerException(String message) {
        super(message);
    }

}
