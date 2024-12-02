package com.bm_nttdata.customer_ms.service;

import com.bm_nttdata.customer_ms.entity.Customer;
import com.bm_nttdata.customer_ms.exception.CustomerNotFoundException;
import com.bm_nttdata.customer_ms.exception.DuplicateCustomerException;
import com.bm_nttdata.customer_ms.mapper.CustomerMapper;
import com.bm_nttdata.customer_ms.model.CustomerRequest;
import com.bm_nttdata.customer_ms.repository.CustomerRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio que gestiona las operaciones de negocio relacionadas con los clientes.
 */
@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    /**
     * Crea un nuevo cliente en el sistema.
     *
     * @param customer el cliente a crear
     * @return el cliente creado con su ID asignado
     */
    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByDocumentNumber(customer.getDocumentNumber())) {
            throw new DuplicateCustomerException("Customer with document number "
                    + customer.getDocumentNumber() + " already exist");
        }

        return customerRepository.save(customer);
    }

    /**
     * Obtiene todos los clientes registrados en el sistema.
     *
     * @return lista de todos los clientes
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Busca un cliente por su ID.
     *
     * @param id el ID del cliente a buscar
     * @return el cliente encontrado
     */
    public Customer getCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id));
    }

    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param id el ID del cliente a actualizar
     * @param request objeto con los nuevos datos del cliente
     * @return el cliente actualizado
     */
    public Customer updateCustomer(String id, CustomerRequest request) {
        Customer customer = getCustomerById(id);
        customerMapper.updateEntity(request, customer);
        return customerRepository.save(customer);
    }

    /**
     * Elimina un cliente del sistema.
     *
     * @param id el ID del cliente a eliminar
     */
    public void deleteCustomer(String id) {
        Customer customer = getCustomerById(id);
        customerRepository.deleteById(id);
    }

}
