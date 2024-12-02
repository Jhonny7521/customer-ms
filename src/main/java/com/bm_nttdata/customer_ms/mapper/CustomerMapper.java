package com.bm_nttdata.customer_ms.mapper;

import com.bm_nttdata.customer_ms.entity.Customer;
import com.bm_nttdata.customer_ms.model.CustomerRequest;
import com.bm_nttdata.customer_ms.model.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Interfaz de mapeo para la conversión entre objetos relacionados con Customer.
 * Utiliza MapStruct para la implementación automática de las conversiones.
 */
@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    /**
     * Convierte un objeto CustomerRequest a una entidad Customer.
     * Ignora los campos id, createdAt y updatedAt durante la conversión.
     * Establece el estado del cliente como "ACTIVE" por defecto.
     *
     * @param request el objeto request con los datos del cliente
     * @return una nueva entidad Customer con los datos mapeados
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    Customer toEntity(CustomerRequest request);

    /**
     * Convierte una entidad Customer a un objeto CustomerResponse.
     *
     * @param customer la entidad Customer a convertir
     * @return objeto CustomerResponse con los datos del cliente
     */
    CustomerResponse toResponse(Customer customer);

    /**
     * Actualiza una entidad Customer existente con los datos de un CustomerRequest.
     * Ignora los campos id y createdAt durante la actualización.
     * Actualiza el campo updatedAt con la fecha y hora actual.
     *
     * @param request objeto con los nuevos datos del cliente
     * @param customer la entidad Customer que será actualizada
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    void updateEntity(CustomerRequest request, @MappingTarget Customer customer);

}
