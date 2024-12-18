package com.bm_nttdata.customer_ms.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bm_nttdata.customer_ms.entity.Customer;
import com.bm_nttdata.customer_ms.exception.CustomerNotFoundException;
import com.bm_nttdata.customer_ms.exception.DuplicateCustomerException;
import com.bm_nttdata.customer_ms.mapper.CustomerMapper;
import com.bm_nttdata.customer_ms.model.CustomerRequest;
import com.bm_nttdata.customer_ms.repository.CustomerRepository;
import com.bm_nttdata.customer_ms.util.CustomerUtils;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

/**
 * Clase de pruebas unitarias para el servicio de customers.
 * Utilizando el patron AAA (Arrange-Act-Assert).
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("Getting all customers")
    void testGetAllCustomers_Success() {
        // Arrange -> Configurations
        when(customerRepository.findAll())
                .thenReturn(CustomerUtils.buildCustomerListMock());
        // Act -> Execution
        List<Customer> customerListResponse = customerService.getAllCustomers();
        // Asserts -> Verifications
        assertAll(
            () -> assertNotNull(customerListResponse,  "List response can't be null"),
            () -> assertEquals(2, customerListResponse.size(), "Debe devol"),
            () -> assertEquals("Luis Sanchez", customerListResponse.get(0).getName()),
            () -> assertEquals("luis.sanchez@nttdata.com", customerListResponse.get(0).getEmail()),
            () -> assertEquals("Marta Jimenez", customerListResponse.get(1).getName()),
            () -> assertEquals("marta.jimenez@nttdata.com", customerListResponse.get(1).getEmail())
        );
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Find a customer by ID")
    void testFindById_Success() {
        // Arrange -> Configurations
        when(customerRepository.findById(any()))
            .thenReturn(Optional.ofNullable(CustomerUtils.buildExistingCustomerMock()));
        // Act -> Execution
        Customer customerResponse = customerService.getCustomerById(any());
        // Asserts -> Verifications
        assertAll(
                () -> assertNotNull(customerResponse),
                () -> assertEquals("Luis Sanchez", customerResponse.getName()),
                () -> assertEquals("ACTIVE", customerResponse.getStatus()),
                () -> assertEquals("PERSONAL", customerResponse.getCustomerType())
        );

        verify(customerRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Find a customer by ID")
    void testFindById_Failed() {
        // Arrange -> Configurations
        String customerId = "675ae6c4a80f7a40908d0cec";

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());
        // Act & Assert -> Execution & Validations
        CustomerNotFoundException exception =
                assertThrows(
                        CustomerNotFoundException.class,
                        () -> customerService.getCustomerById(customerId));

        assertEquals(
                "Customer not found with id: 675ae6c4a80f7a40908d0cec",
                exception.getMessage());

        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("Create a customer - success")
    void testCreateCustomer_Success() {

        // Arrange -> Configurations
        Customer newCustomer = CustomerUtils.buildNewCustomerMock();
        Customer customerExpected = CustomerUtils.buildExistingCustomerMock();

        when(customerRepository.existsByDocumentNumber(newCustomer.getDocumentNumber()))
                .thenReturn(false);
        when(customerRepository.save(newCustomer))
                .thenReturn(customerExpected);

        // Act -> Execution
        Customer customerResponse = customerService.createCustomer(newCustomer);

        // Asserts -> Verifications
        assertNotNull(customerResponse);
        assertEquals(customerExpected, customerResponse);
        assertEquals("Luis Sanchez", customerResponse.getName());
        assertEquals("PERSONAL", customerResponse.getCustomerType());
        assertEquals("ACTIVE", customerResponse.getStatus());

        verify(customerRepository, times(1)).existsByDocumentNumber(any(String.class));
        verify(customerRepository, times(1)).save(any(Customer.class));

    }

    @Test
    @DisplayName("Create a customer - failed")
    void testCreateCustomer_Failed() {

        // Arrange -> Configurations
        Customer newCustomer = CustomerUtils.buildNewCustomerMock();

        when(customerRepository.existsByDocumentNumber(newCustomer.getDocumentNumber()))
            .thenReturn(true);
        // Act & Assert -> Execution & Verifications
        DuplicateCustomerException exception =
            assertThrows(DuplicateCustomerException.class,
                () -> customerService.createCustomer(newCustomer),
                "Should throw DuplicateCustomerException when the document already exists.");

        assertEquals("Customer with document number "
            + newCustomer.getDocumentNumber() + " already exist",
            exception.getMessage());

        verify(customerRepository, times(1)).existsByDocumentNumber(any(String.class));
        verify(customerRepository, never()).save(any(Customer.class));

    }

    @Test
    @DisplayName("Update a customer - Success")
    void testUpdateCustomer_Success() {

        // Arrange -> Configurations
        final String customerId = "675ae6c4a80f7a40908d0cec";
        Customer existingCustomer = CustomerUtils.buildExistingCustomerMock();
        CustomerRequest customerRequest = CustomerUtils.buildCustomerRequestMock();

        Customer updatedCustomer = new Customer();

        BeanUtils.copyProperties(existingCustomer, updatedCustomer);
        updatedCustomer.setAddress(customerRequest.getAddress());
        updatedCustomer.setPhone(customerRequest.getPhone());

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class)))
                .thenReturn(updatedCustomer);

        // Act -> Execution
        Customer customerResponse = customerService.updateCustomer(customerId, customerRequest);

        // Asserts -> Verifications
        assertAll(
                () -> assertNotNull(
                        customerResponse,
                        "The updated customer must not be null."),
                () -> assertEquals(
                        customerId,
                        updatedCustomer.getId(),
                        "The customer's id must be the same"),
                () -> assertNotEquals(
                        customerResponse.getAddress(),
                        existingCustomer.getAddress(),
                        "The customer's address must be different"),
                () -> assertNotEquals(
                        customerResponse.getPhone(),
                        existingCustomer.getPhone(),
                        "The customer's phone must be different"),
                () -> verify(customerRepository, times(1)).findById(customerId),
                () -> verify(customerRepository, times(1)).save(any(Customer.class))
        );

    }

    @Test
    @DisplayName("Update a customer - Failed")
    void testUpdateCustomer_Failed() {

        // Arrange -> Configurations
        String customerId = "675ae6c4a80f7";
        CustomerRequest customerRequest = CustomerUtils.buildCustomerRequestMock();

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());

        // Act & Assert -> Execution & Verifications
        CustomerNotFoundException exception =
                assertThrows(
                        CustomerNotFoundException.class,
                        () -> customerService.updateCustomer(customerId, customerRequest),
                        "Should throw CustomerNotFoundException when customer does not exist.");
        assertEquals(
                "Customer not found with id: 675ae6c4a80f7",
                exception.getMessage());

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).save(any(Customer.class));

    }

    @Test
    @DisplayName("Delete customer - Success")
    void testDeleteCustomer_Success() {

        // Arrange -> Configurations
        String customerId = "675ae6c4a80f7a40908d0cec";
        Customer customer = CustomerUtils.buildExistingCustomerMock();

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(customer);

        // Act & Assert -> Execution & Verifications
        assertDoesNotThrow(() -> customerService.deleteCustomer(customerId),
                "Should not throw exception when deleting an existing customer");

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).delete(any(Customer.class));

    }

    @Test
    @DisplayName("Delete customer - Success")
    void testDeleteCustomer_Failed() {

        // Arrange -> Configurations
        String customerId = "675ae6c4a80f7a";

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());

        CustomerNotFoundException exception =
                assertThrows(CustomerNotFoundException.class,
                        () -> customerService.deleteCustomer(customerId));

        // Act & Assert -> Execution & Verifications
        assertEquals("Customer not found with id: 675ae6c4a80f7a", exception.getMessage());
        assertTrue(exception.getMessage().contains(customerId),
                "The error message must contain the customer ID");
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).delete(any(Customer.class));

    }
    
}
