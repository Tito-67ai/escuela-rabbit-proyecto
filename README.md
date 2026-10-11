# Escuela Rabbit

Sistema de gestión escolar: microservicios **Spring Boot** + frontend **Angular** (Eureka, Gateway, Config Server, JWT, RabbitMQ).

## 🔗 Swagger

| Servicio | URL |
| --- | --- |
| Auth (usuarios / JWT) | http://localhost:8084/swagger-ui/index.html |
| Admin (personal) | http://localhost:8083/swagger-ui/index.html |
| Alumno | http://localhost:8081/swagger-ui/index.html |
| Curso | http://localhost:8082/swagger-ui/index.html |
| Config | http://localhost:8888/swagger-ui/index.html |
| Gateway | http://localhost:8080/webjars/swagger-ui/index.html |

Login: `POST /auth/login` → copiar el token → botón **Authorize** (pegar **solo el token**).
El Gateway no usa `/swagger-ui/index.html`, sino `/webjars/swagger-ui/index.html`.

## 🚀 Levantar

```bash
docker compose up -d --build
```

| Acceso | URL |
| --- | --- |
| Frontend | http://localhost:4200 |
| API (Gateway) | http://localhost:8080 |
| RabbitMQ | http://localhost:15672 (`guest` / `guest`) |
| Monitoreo | http://localhost:8090 |

## 👤 Usuarios de prueba

`admin`/`admin777` (ROOT) · `administrativo`/`administrativo123` · `preceptor`/`preceptor123` · `docente`/`docente123` · `director`/`director123`

## 🌿 Ramas

`main` (proyecto completo) · `backend` · `frontend` · `config` (configuración que sirve el Config Server)
