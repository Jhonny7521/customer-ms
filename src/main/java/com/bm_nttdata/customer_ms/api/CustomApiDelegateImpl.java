package com.bm_nttdata.customer_ms.api;

import com.bm_nttdata.customer_ms.entity.Customer;
import com.bm_nttdata.customer_ms.mapper.CustomerMapper;
import com.bm_nttdata.customer_ms.model.CustomerRequest;
import com.bm_nttdata.customer_ms.model.CustomerResponse;
import com.bm_nttdata.customer_ms.service.CustomerService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Implementación de los métodos generados por OpenApi Generator.
 * Gestiona las operaciones definidas en el contrato OpenApi para el manejo de clientes.
 */
@Slf4j
@Component
public class CustomApiDelegateImpl implements CustomerApiDelegate {
    @Autowired
    private CustomerService customerService;
    private CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @Override
    public ResponseEntity<CustomerResponse> createCustomer(CustomerRequest customerRequest) {
        log.info("Creating customer with document number: {}", customerRequest.getDocumentNumber());

        Customer customer = customerMapper.toEntity(customerRequest);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        Customer savedCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(customerMapper.toResponse(savedCustomer));
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(String id) {
        log.info("Deleting customer with id: {}", id);
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        log.info("Getting all customers");
        List<CustomerResponse> customers = customerService.getAllCustomers()
                .stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomerById(String id) {
        log.info("Getting customer with id: {}", id);
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerMapper.toResponse(customer));
    }

    @Override
    public ResponseEntity<CustomerResponse> updateCustomer(
            String id,
            CustomerRequest customerRequest) {
        log.info("Updating customer with id: {}", id);
        Customer updatedCustomer = customerService.updateCustomer(id, customerRequest);
        return ResponseEntity.ok(customerMapper.toResponse(updatedCustomer));
    }

}
