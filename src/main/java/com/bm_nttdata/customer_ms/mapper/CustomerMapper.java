package com.bm_nttdata.customer_ms.mapper;

import com.bm_nttdata.customer_ms.entity.Customer;
import com.bm_nttdata.customer_ms.model.CustomerRequest;
import com.bm_nttdata.customer_ms.model.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    Customer toEntity(CustomerRequest request);

    CustomerResponse toResponse(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    void updateEntity(CustomerRequest request, @MappingTarget Customer customer);

}
