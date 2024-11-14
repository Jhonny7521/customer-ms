package com.bm_nttdata.customer_ms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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
