package com.bm_nttdata.customer_ms.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidad que representa a los clientes en el sistema bancario.
 * Esta clase es un documento de MongoDB que almacena la información
 * básica de los clientes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customer")
public class Customer {

    @Id
    private String id;
    private String customerType;
    private String name;
    private String documentNumber;
    private String email;
    private String phone;
    private String address;
    private String status;
    //@CreatedDate
    private LocalDateTime createdAt;
    //@LastModifiedDate
    private LocalDateTime updatedAt;

}
