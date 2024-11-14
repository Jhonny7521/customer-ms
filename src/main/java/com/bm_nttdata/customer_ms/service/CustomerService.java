package com.bm_nttdata.customer_ms.service;

import com.bm_nttdata.customer_ms.entity.Customer;
import com.bm_nttdata.customer_ms.exception.CustomerNotFoundException;
import com.bm_nttdata.customer_ms.exception.DuplicateCustomerException;
import com.bm_nttdata.customer_ms.mapper.CustomerMapper;
import com.bm_nttdata.customer_ms.model.CustomerRequest;
import com.bm_nttdata.customer_ms.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    public Customer createCustomer(Customer customer){
        if (customerRepository.existsByDocumentNumber(customer.getDocumentNumber())){
            throw new DuplicateCustomerException("Customer with document number " + customer.getDocumentNumber() + " already exist");
        }

        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(String id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
    }

    public Customer updateCustomer(String id, CustomerRequest request){
        Customer customer = getCustomerById(id);
        customerMapper.updateEntity(request, customer);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(String id){
        Customer customer = getCustomerById(id);
        customerRepository.deleteById(id);
    }

}
