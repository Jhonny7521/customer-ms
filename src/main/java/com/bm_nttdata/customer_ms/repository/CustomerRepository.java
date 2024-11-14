package com.bm_nttdata.customer_ms.repository;

import com.bm_nttdata.customer_ms.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByDocumentNumber(String documentNumber);
    boolean existsByDocumentNumber(String documentNumber);
}
