package com.bm_nttdata.customer_ms.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bm_nttdata.customer_ms.entity.Customer;
import com.bm_nttdata.customer_ms.exception.CustomerNotFoundException;
import com.bm_nttdata.customer_ms.exception.DuplicateCustomerException;
import com.bm_nttdata.customer_ms.mapper.CustomerMapper;
import com.bm_nttdata.customer_ms.model.CustomerRequest;
import com.bm_nttdata.customer_ms.model.CustomerResponse;
import com.bm_nttdata.customer_ms.service.CustomerService;
import com.bm_nttdata.customer_ms.util.CustomerUtils;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Clase de pruebas unitarias para la implementacion del delegado generadp
 * por OpenApi Generator.
 * Se utiliza el patron AAA (Arrange-Act-Assert).
 */
@ExtendWith(MockitoExtension.class)
class CustomerApiDelegateImplTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomApiDelegateImpl customerApiDelegate;

    @Test
    @DisplayName("Get all customers - Success with multiple customers")
    void testGetAllCustomers_Success() {

        // Arrange -> Configurations
        List<Customer> customerList = CustomerUtils.buildCustomerListMock();
        List<CustomerResponse> customerResponseList = CustomerUtils.buildCustomerResponseListMock();

        when(customerService.getAllCustomers())
                .thenReturn(customerList);
        when(customerMapper.toResponse(customerList.get(0)))
                .thenReturn(customerResponseList.get(0));
        when(customerMapper.toResponse(customerList.get(1)))
                .thenReturn(customerResponseList.get(1));

        // Act -> Execution
        ResponseEntity<List<CustomerResponse>> response = customerApiDelegate.getAllCustomers();

        // Asserts -> Verifications
        assertNotNull(response, "The answer can't be null.");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "The status code must be 200");
        assertNotNull(response.getBody(), "the response body can't be null");
        assertEquals(2, response.getBody().size(), "Must return 2 customers");
        assertEquals(
                customerResponseList,
                response.getBody(),
                "The response body is not as expected");
        assertEquals(
                "Luis Sanchez",
                response.getBody().get(0).getName(),
                "First customer name must match");
        assertEquals(
                CustomerResponse.CustomerTypeEnum.PERSONAL,
                response.getBody().get(0).getCustomerType(),
                "First customer name must match");

        verify(customerService, times(1)).getAllCustomers();
        verify(customerMapper, times(2)).toResponse(any(Customer.class));

    }

    @Test
    @DisplayName("Get all customers - Success with empty list")
    void testGetAllCustomers_EmptyList() {

        // Arrange -> Configurations
        when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        // Act -> Execution
        ResponseEntity<List<CustomerResponse>> response = customerApiDelegate.getAllCustomers();

        // Asserts -> Verifications
        assertNotNull(response, "The answer can't be null.");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "The status code must be 200");
        assertNotNull(response.getBody(), "The response body can't be null");
        assertTrue(response.getBody().isEmpty(), "The list must be empty");

        verify(customerService, times(1)).getAllCustomers();
        verify(customerMapper, never()).toResponse(any(Customer.class));
    }

    @Test
    @DisplayName("Get customer by Id - Return CustomerResponse if customer exist")
    void testGetCustomerById_ReturnCustomerResponse() {

        // Arrange -> Configurations
        String customerId = "675ae6c4a80f7a40908d0cec";
        Customer customer = CustomerUtils.buildExistingCustomerMock();
        CustomerResponse customerResponse = CustomerUtils.buildCustomerResponseMock();

        when(customerService.getCustomerById(customerId))
                .thenReturn(customer);
        when(customerMapper.toResponse(customer))
                .thenReturn(customerResponse);

        // Act -> Execution
        ResponseEntity<CustomerResponse> response = customerApiDelegate.getCustomerById(customerId);

        // Asserts -> Verifications
        assertNotNull(response, "The answer can't be null.");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "The status code must be 200.");
        assertEquals(
                customerResponse,
                response.getBody(),
                "The response body is not as expected");
        assertEquals(
                "Luis Sanchez",
                response.getBody().getName(),
                "Customer name must match");

        verify(customerService, times(1)).getCustomerById(customerId);
        verify(customerMapper, times(1)).toResponse(any(Customer.class));
    }

    @Test
    @DisplayName("Get customer by Id - Throw exception if customer not found")
    void testGetCustomerById_CustomerNotFound() {

        // Arrange -> Configurations
        String customerId = "675ae6c4a80f7a40908d0cec";

        when(customerService.getCustomerById(customerId))
            .thenThrow(new CustomerNotFoundException("Customer not found with id: " + customerId));

        // Act -> Execution / Asserts -> Verifications
        CustomerNotFoundException exception =
            assertThrows(
                CustomerNotFoundException.class,
                () -> customerApiDelegate.getCustomerById(customerId));

        assertTrue(
                exception.getMessage().contains(customerId),
                "Exception message must contain the customer ID");

        verify(customerService, times(1)).getCustomerById(customerId);
        verify(customerMapper, never()).toResponse(any(Customer.class));
    }

    @Test
    @DisplayName("Create customer - Success")
    void testCreateCustomer_Success() {

        // Arrange -> Configurations
        CustomerRequest customerRequest = CustomerUtils.buildCustomerRequestMock();
        Customer customer = CustomerUtils.buildNewCustomerMock();
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        Customer customerCreated = CustomerUtils.buildExistingCustomerMock();
        CustomerResponse customerResponse = CustomerUtils.buildCustomerResponseMock();

        when(customerMapper.toEntity(customerRequest)).thenReturn(customer);

        when(customerService.createCustomer(customer))
                .thenReturn(customerCreated);

        when(customerMapper.toResponse(customerCreated)).thenReturn(customerResponse);

        // Act -> Execution
        ResponseEntity<CustomerResponse> response =
                customerApiDelegate.createCustomer(customerRequest);

        // Asserts -> Verifications
        assertNotNull(response, "The answer can't be null.");
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "The status code must be 200.");
        assertEquals(
                customerResponse,
                response.getBody(),
                "The response body is not as expected");
        assertEquals(
                "Luis Sanchez",
                response.getBody().getName(),
                "Customer name must match");

        verify(customerMapper, times(1)).toEntity(any(CustomerRequest.class));
        verify(customerService, times(1)).createCustomer(customer);
        verify(customerMapper, times(1)).toResponse(any(Customer.class));

    }

    @Test
    @DisplayName("Create customer - Failed Customer exist")
    void testCreateCustomer_FailedCustomerExist() {

        // Arrange -> Configurations
        CustomerRequest customerRequest = CustomerUtils.buildCustomerRequestMock();
        Customer customer = CustomerUtils.buildNewCustomerMock();
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        when(customerMapper.toEntity(customerRequest)).thenReturn(customer);

        when(customerService.createCustomer(any(Customer.class)))
            .thenThrow(
                new DuplicateCustomerException("Customer with document number "
                    + customer.getDocumentNumber() + " already exist"));

        // Act -> Execution
        // Asserts -> Verifications
        DuplicateCustomerException exception = assertThrows(DuplicateCustomerException.class,
                () -> customerApiDelegate.createCustomer(customerRequest),
                "Should throw DuplicateCustomerException when the document already exists.");

        assertTrue(exception.getMessage().contains(customer.getDocumentNumber()),
                "The error message must contain the customer ID");

        verify(customerMapper, times(1)).toEntity(any(CustomerRequest.class));
        verify(customerService, times(1)).createCustomer(customer);
        verify(customerMapper, never()).toResponse(any(Customer.class));

    }

    @Test
    @DisplayName("Update customer - Success")
    void testUpdateCustomer_Success() {

        // Arrange -> Configurations
        final String customerId = "675ae6c4a80f7a40908d0cec";
        CustomerRequest customerRequest = CustomerUtils.buildCustomerRequestMock();
        Customer customerUpdated = CustomerUtils.buildExistingCustomerMock();
        customerUpdated.setAddress(customerRequest.getAddress());
        customerUpdated.setPhone(customerRequest.getPhone());
        customerUpdated.setUpdatedAt(LocalDateTime.now());

        CustomerResponse customerResponse = CustomerUtils.buildCustomerResponseMock();

        when(customerService.updateCustomer(customerId, customerRequest))
                .thenReturn(customerUpdated);

        when(customerMapper.toResponse(customerUpdated)).thenReturn(customerResponse);

        // Act -> Execution
        ResponseEntity<CustomerResponse> response =
                customerApiDelegate.updateCustomer(customerId, customerRequest);

        // Asserts -> Verifications
        assertNotNull(response, "The answer can't be null.");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "The status code must be 200.");
        assertEquals(customerResponse, response.getBody(), "The response body is not as expected");

        verify(customerService, times(1))
                .updateCustomer(eq(customerId), any(CustomerRequest.class));
        verify(customerMapper, times(1)).toResponse(any(Customer.class));

    }

    @Test
    @DisplayName("Update customer - Failed customer not exist")
    void testUpdateCustomer_FailedCustomerNotExist() {

        // Arrange -> Configurations
        String customerId = "675ae6c4a80f7a40908d0cec";
        CustomerRequest customerRequest = CustomerUtils.buildCustomerRequestMock();

        when(customerService.updateCustomer(customerId, customerRequest))
            .thenThrow(new CustomerNotFoundException("Customer not found with id: " + customerId));

        // Act -> Execution
        // Asserts -> Verifications
        CustomerNotFoundException exception =
                assertThrows(
                        CustomerNotFoundException.class,
                        () -> customerApiDelegate.updateCustomer(customerId, customerRequest),
                        "Should throw CustomerNotFoundException when customer not exists.");

        assertTrue(exception.getMessage().contains(customerId),
                "The error message must contain the customer ID");

        verify(customerService, times(1))
                .updateCustomer(eq(customerId), any(CustomerRequest.class));
        verify(customerMapper, never()).toResponse(any(Customer.class));

    }
}
