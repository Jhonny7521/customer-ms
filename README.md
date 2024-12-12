# Customer Service

## Descripción
Este microservicio gestiona todas las operaciones relacionadas con los clientes para el sistema bancario. Gestiona tanto los clientes personales como los empresariales, proporcionando operaciones CRUD y validaciones de acuerdo con las reglas de negocio.

## Arquitectura del sistema

![Banking System Architecture](./src/main/resources/docs/system_bank.jpg)

## Links de repositorios

* [Microservicio Account](https://github.com/Jhonny7521/account-ms)
* [Microservicio Credit](https://github.com/Jhonny7521/credit-ms)
* [Microservicio Transactions](https://github.com/Jhonny7521/transaction-ms)
* [Microservicio Reports](https://github.com/Jhonny7521/report-ms)
* [Microservicio DebitCard](https://github.com/Jhonny7521/debitcard-ms)
* [AuthService](https://github.com/Jhonny7521/auth-service)
* [ApiGateway](https://github.com/Jhonny7521/api-gateway-ms)
* [DiscoveryServer](https://github.com/Jhonny7521/discovery-server-ms)
* [ConfigServer](https://github.com/Jhonny7521/config-server-ms)

## Tecnologías

* Java 17
* Spring Boot 3.3.5
* MongoDB
* Maven
* Lombok
* MapStruct
* OpenAPI Generator

## Características

- Gestión de clientes (particulares y empresas)
- Validación de documentos
- Gestión del estado del cliente
- Integración con otros servicios para la validación de clientes

## API Endpoints

```
POST   /api/customers          - Crear un nuevo cliente
GET    /api/customers          - Obtener todos los clientes  
GET    /api/customers/{id}     - Obtener cliente por ID
PUT    /api/customers/{id}     - Actualizar cliente
DELETE /api/customers/{id}     - Eliminar cliente
```

## Prerequisitos

* JDK 17
* MongoDB

## Documentación API 
Acceda a la documentación de OpenAPI en:

`url`: http://localhost:8585/swagger-ui.html
