# Microservicio Delete en Spring Boot con MariaDB

Este proyecto es un microservicio desarrollado en **Spring Boot** que implementa un DELETE sobre la entidad **Producto**. La aplicación utiliza **MariaDB** como base de datos relacional y está diseñada para ser fácilmente desplegada mediante contenedores **Docker** y gestionada con **Kubernetes**. Además, incluye **pruebas unitarias**, **de integración** y **documentación en Postman** para garantizar su correcto funcionamiento.

---

## Características

- Funcionalidad de Eliminar producto.
- Base de datos relacional MariaDB.
- Contenerización con Kubernetes.
- Orquestación de servicios con Kubernetes.
- Pruebas unitarias (JUnit + Mockito).


---

## Requisitos previos

- Java 17
- Docker y Docker Compose instalados
- Maven Wrapper (`./mvnw`)
- WSL2 (en caso de uso en Windows)

---

## Uso

El microservicio expone una API REST para interactuar con la entidad. A continuación, se describen los endpoints principales:


- `PUT /eliminar/api/productos/eliminar/{codigo}`: Actualizar un producto por su código.

---

## Tecnologías utilizadas

- Spring Boot
- MariaDB
- Docker
- Docker Compose
- Kubernetes
- JUnit 5
- Mockito
- Testcontainers
- JaCoCo
- Postman

---

## Autor

Alejandro Castaño 

