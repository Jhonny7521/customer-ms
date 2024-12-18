package com.bm_nttdata.customer_ms.util;

import com.bm_nttdata.customer_ms.entity.Customer;
import com.bm_nttdata.customer_ms.model.CustomerRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Clase utils para generacion de mocks relacionados a Customers.
 */
public class CustomerUtils {

    /**
     * Construye una instancia de la entidad Customer.
     *
     * @return el objeto generado
     */
    public static Customer buildCustomerMock() {
        return Customer.builder()
                .id("675ae6c4a80f7a40908d0cec")
                .customerType("PERSONAL")
                .name("Luis Sanchez")
                .documentNumber("45678921")
                .email("luis.sanchez@nttdata.com")
                .phone("98764123")
                .address("Calle las begonias Mz. A Lte 45")
                .status("ACTIVE")
                .build();
    }

    /**
     * Construye una lista de instancias de Customers.
     *
     * @return la lista con objetos Customer generada
     */
    public static List<Customer> buildCustomerListMock() {
        Customer customer1 = Customer.builder()
                .customerType("PERSONAL")
                .name("Luis Sanchez")
                .documentNumber("45678921")
                .email("luis.sanchez@nttdata.com")
                .phone("98764123")
                .address("Calle las begonias Mz. A Lte 45")
                .status("ACTIVE")
                .build();
        Customer customer2 = Customer.builder()
                .customerType("BUSINESS")
                .name("Marta Jimenez")
                .documentNumber("57964261")
                .email("marta.jimenez@nttdata.com")
                .phone("999534625")
                .address("Calle Los troyanos, Surquillo")
                .status("ACTIVE")
                .build();
        return Arrays.asList(customer1, customer2);
    }

    /**
     *  Construye una instancias de CustomerRequest.
     *
     * @return el CustomerRequest generado
     */
    public static CustomerRequest buildCustomerRequestMock() {
        CustomerRequest customerRequest = new CustomerRequest();

        customerRequest.setName("Luis Sanchez");
        customerRequest.setCustomerType(CustomerRequest.CustomerTypeEnum.PERSONAL);
        customerRequest.setAddress("Pasaje los Claveles Mz. J Lte 81");
        customerRequest.setEmail("luis.sanchez@nttdata.com");
        customerRequest.phone("980000126");
        customerRequest.setDocumentNumber("45678921");

        return customerRequest;
    }

}
