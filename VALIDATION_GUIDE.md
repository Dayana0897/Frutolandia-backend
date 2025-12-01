# Guía de Validaciones y Manejo de Errores

## 📋 Validaciones Implementadas

### **Product (Producto)**

| Campo | Validaciones | Mensaje de Error |
|-------|-------------|------------------|
| `name` | `@NotBlank`, `@Size(min=2, max=100)` | "El nombre del producto es obligatorio" / "El nombre debe tener entre 2 y 100 caracteres" |
| `price` | `@NotNull`, `@DecimalMin(0.01)` | "El precio es obligatorio" / "El precio debe ser mayor a 0" |
| `ingredients` | `@Size(max=500)` | "Los ingredientes no pueden superar los 500 caracteres" |
| `description` | `@Size(max=1000)` | "La descripción no puede superar los 1000 caracteres" |
| `stockQuantity` | `@Min(0)` | "La cantidad en stock no puede ser negativa" |

### **User (Usuario)**

| Campo | Validaciones | Mensaje de Error |
|-------|-------------|------------------|
| `email` | `@NotBlank`, `@Email` | "El email es obligatorio" / "Debe proporcionar un email válido" |
| `password` | `@NotBlank`, `@Size(min=6)` | "La contraseña es obligatoria" / "La contraseña debe tener al menos 6 caracteres" |
| `name` | `@NotBlank`, `@Size(min=2, max=100)` | "El nombre es obligatorio" / "El nombre debe tener entre 2 y 100 caracteres" |
| `role` | `@NotNull` | "El rol es obligatorio" |

---

## 🚨 Excepciones Personalizadas

### **ResourceNotFoundException**
- **Código HTTP**: `404 NOT FOUND`
- **Uso**: Cuando no se encuentra un recurso por ID o campo específico
- **Ejemplo**: Buscar un producto con ID inexistente

### **DuplicateResourceException**
- **Código HTTP**: `409 CONFLICT`
- **Uso**: Cuando se intenta crear un recurso con datos únicos ya existentes
- **Ejemplo**: Registrar un usuario con email duplicado

### **BadRequestException**
- **Código HTTP**: `400 BAD REQUEST`
- **Uso**: Para peticiones malformadas o con datos inválidos
- **Ejemplo**: Formato de datos incorrecto

### **MethodArgumentNotValidException**
- **Código HTTP**: `400 BAD REQUEST`
- **Uso**: Errores de validación de Bean Validation
- **Respuesta**: Incluye un mapa con todos los campos inválidos y sus mensajes

---

## 📄 Formato de Respuestas de Error

### Estructura Estándar

```json
{
  "timestamp": "2025-12-01T10:30:45",
  "status": 404,
  "error": "Not Found",
  "message": "Producto no encontrado con id: '123'",
  "path": "/api/products/123"
}
```

### Error de Validación (con campos múltiples)

```json
{
  "timestamp": "2025-12-01T10:30:45",
  "status": 400,
  "error": "Validation Error",
  "message": "Errores de validación en los campos",
  "path": "/api/products",
  "validationErrors": {
    "name": "El nombre del producto es obligatorio",
    "price": "El precio debe ser mayor a 0"
  }
}
```

---

## 🔧 Ejemplos de Uso

### Crear Producto (Request válido)

```json
POST /api/products
{
  "name": "Batido de Fresa",
  "price": 5.99,
  "ingredients": "Fresas, leche, azúcar",
  "description": "Delicioso batido natural de fresas",
  "stockQuantity": 50
}
```

**Respuesta**: `201 CREATED` con el producto creado

### Crear Producto (Request inválido)

```json
POST /api/products
{
  "name": "B",
  "price": -5.0,
  "stockQuantity": -10
}
```

**Respuesta**: `400 BAD REQUEST` con errores de validación:
```json
{
  "timestamp": "2025-12-01T10:30:45",
  "status": 400,
  "error": "Validation Error",
  "message": "Errores de validación en los campos",
  "path": "/api/products",
  "validationErrors": {
    "name": "El nombre debe tener entre 2 y 100 caracteres",
    "price": "El precio debe ser mayor a 0",
    "stockQuantity": "La cantidad en stock no puede ser negativa"
  }
}
```

### Buscar Producto Inexistente

```
GET /api/products/999
```

**Respuesta**: `404 NOT FOUND`
```json
{
  "timestamp": "2025-12-01T10:30:45",
  "status": 404,
  "error": "Not Found",
  "message": "Producto no encontrado con id: '999'",
  "path": "/api/products/999"
}
```

### Crear Usuario con Email Duplicado

```json
POST /api/users
{
  "email": "admin@frutolandia.com",
  "password": "123456",
  "name": "Admin",
  "role": "ADMIN"
}
```

**Respuesta**: `409 CONFLICT` (si el email ya existe)
```json
{
  "timestamp": "2025-12-01T10:30:45",
  "status": 409,
  "error": "Conflict",
  "message": "Usuario ya existe con email: 'admin@frutolandia.com'",
  "path": "/api/users"
}
```

---

## ✅ Beneficios Implementados

1. **Validación Automática**: Los datos se validan antes de llegar a la lógica de negocio
2. **Respuestas Consistentes**: Todos los errores siguen el mismo formato
3. **Mensajes Claros**: El frontend puede mostrar mensajes específicos al usuario
4. **Seguridad**: Se previenen datos inválidos en la base de datos
5. **Mantenibilidad**: Fácil agregar nuevas validaciones o excepciones
6. **Transaccionalidad**: Uso de `@Transactional` para garantizar consistencia

---

## 🔄 Próximos Pasos Recomendados

- [ ] Implementar Spring Security con JWT
- [ ] Encriptar contraseñas con BCrypt
- [ ] Crear DTOs separados de las entidades
- [ ] Agregar validaciones personalizadas complejas
- [ ] Implementar tests unitarios para validaciones
- [ ] Agregar logging estructurado de errores
