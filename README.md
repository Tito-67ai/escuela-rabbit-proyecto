# Escuela Rabbit — Sistema de Gestión Escolar

Proyecto full-stack: backend de **microservicios Spring Boot** + frontend **Angular**.
Arquitectura orientada a microservicios con descubrimiento de servicios, API Gateway y configuración centralizada, protegidos con JWT y control de acceso por roles.

## Estructura

```
escuela-rabbit-proyecto/
├── backend/                      # Microservicios Spring Boot (Java 17+)
│   ├── eureka-server/            # Servidor de descubrimiento (Eureka) — puerto 8761
│   ├── config-server/            # Configuración centralizada (Spring Cloud Config) — puerto 8888
│   ├── gateway-service/          # API Gateway (Spring Cloud Gateway) — puerto 8080
│   ├── auth-service/             # Autenticación y usuarios (JWT) — puerto 8084
│   ├── admin-service/            # Gestión de personal — puerto 8083
│   ├── alumno-service/           # Gestión de alumnos — puerto 8081
│   └── curso-service/            # Gestión de cursos — puerto 8082
└── frontend/                     # Aplicación Angular (ng serve — puerto 4200)
```

## Roles

| Rol | Permisos principales |
| --- | --- |
| `ROOT` | Registrar usuarios, gestionar personal y cursos |
| `ADMINISTRATIVO` | Gestionar personal |
| `PRECEPTOR` | Registrar alumnos y cursos |
| `DOCENTE` | Consultas de solo lectura |
| `DIRECTOR` | Consultas de solo lectura |

Usuarios de prueba (seed): `admin/admin777`, `preceptor/preceptor123`, `docente/docente123`, `director/director123`, `administrativo/administrativo123`.

## Arranque rápido (backend)

Orden de inicio desde `backend/` (cada módulo con `./mvnw.cmd spring-boot:run`):

1. `eureka-server` (8761)
2. `config-server` (8888)
3. `auth-service` (8084)
4. `admin-service` (8083)
5. `alumno-service` (8081)
6. `curso-service` (8082)
7. `gateway-service` (8080)

Con todo arriba, el front corre con `ng serve` desde `frontend/` y se accede en `http://localhost:4200`.

## Documentación de APIs (Swagger UI)

- Eureka: `http://localhost:8761/swagger-ui/index.html`
- Config: `http://localhost:8888/swagger-ui/index.html`
- Gateway (agrega auth + docs por servicio): `http://localhost:8080/swagger-ui.html`

Login vía gateway: `POST http://localhost:8080/auth/login` con `{ "username": "...", "password": "..." }` → devuelve `{ token, rol }`. El resto de los endpoints exigen `Authorization: Bearer <token>`.