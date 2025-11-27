# Frutolandia Backend

Backend API para la aplicación Frutolandia desarrollado con Spring Boot 3.3.5

## 🚀 Despliegue Rápido

### Requisitos
- Java 21
- Maven 3.x

### Comandos de Despliegue

```bash
# Opción 1: Ejecutar directamente con Maven
mvn spring-boot:run

# Opción 2: Compilar y ejecutar el JAR
mvn clean package
java -jar target/frutolandia-backend-1.0.0.jar
```

**Acceso**: http://localhost:8080  
**Consola H2**: http://localhost:8080/h2-console (URL: `jdbc:h2:mem:testdb`, Usuario: `sa`, Sin contraseña)

## Características

- **Gestión de Usuarios**: Crear, actualizar, eliminar y consultar usuarios
  - Roles: USER y ADMIN
  - Autenticación por email
  
- **Gestión de Productos**: CRUD completo de productos
  - Campos: ID, nombre, precio, ingredientes
  - Búsqueda por nombre
  - Gestión de stock

## Tecnologías

- Spring Boot 3.3.5
- Spring Data JPA
- H2 Database (desarrollo)
- MySQL (producción)
- Lombok
- Maven

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/frutolandia/
│   │   ├── controller/      # Controladores REST
│   │   ├── model/           # Entidades JPA
│   │   ├── repository/      # Repositorios
│   │   ├── service/         # Lógica de negocio
│   │   └── FrutollandiaBackendApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

## Instalación y Ejecución

### Prerrequisitos
- Java 17+
- Maven 3.8.1+

### Pasos

1. **Navegar al directorio del backend**
```bash
cd backend
```

2. **Compilar el proyecto**
```bash
mvn clean install
```

3. **Ejecutar la aplicación**
```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## API Endpoints

### Usuarios
- `POST /api/users` - Crear usuario
- `GET /api/users` - Obtener todos los usuarios
- `GET /api/users/{id}` - Obtener usuario por ID
- `GET /api/users/email/{email}` - Obtener usuario por email
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Eliminar usuario

### Productos
- `POST /api/products` - Crear producto
- `GET /api/products` - Obtener todos los productos
- `GET /api/products/{id}` - Obtener producto por ID
- `GET /api/products/search?name={name}` - Buscar productos por nombre
- `PUT /api/products/{id}` - Actualizar producto
- `DELETE /api/products/{id}` - Eliminar producto

## Base de Datos

### Tabla: users
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL
);
```

### Tabla: products
```sql
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    ingredients TEXT,
    description TEXT,
    stock_quantity INT
);
```

## Configuración

Editar `src/main/resources/application.properties`:

### Desarrollo (H2)
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

### Producción (MySQL)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/frutolandia
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

## Ejemplos de Uso

### Crear Usuario
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@frutolandia.com",
    "password": "password123",
    "name": "Administrador",
    "role": "ADMIN"
  }'
```

### Crear Producto
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Fresa Natural",
    "price": 5.99,
    "ingredients": "Fresa 100%",
    "description": "Fresas frescas de temporada",
    "stockQuantity": 100
  }'
```

## Desarrollo

Para generar cambios en la base de datos:
```bash
mvn hibernate:hbm2ddl
```

## Licencia

Proyecto académico - TFM Atrium
