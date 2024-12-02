package com.bm_nttdata.customer_ms.repository;

import com.bm_nttdata.customer_ms.entity.Customer;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repositorio para la gestión de entidades Customer en MongoDB.
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    Optional<Customer> findByDocumentNumber(String documentNumber);

    boolean existsByDocumentNumber(String documentNumber);
}
