swagger

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

docker

git clone https://github.com/Tito-67ai/escuela-rabbit-proyecto.git
cd escuela-rabbit-proyecto
docker compose up -d --build

| Acceso | URL |
| --- | --- |
| Frontend | http://localhost:4200 |
| API (Gateway) | http://localhost:8080 |
| RabbitMQ | http://localhost:15672 (`guest` / `guest`) |
| Monitoreo | http://localhost:8090 |

usuarios para login y sacar token

`admin`/`admin777` (ROOT) · `administrativo`/`administrativo123` · `preceptor`/`preceptor123` · `docente`/`docente123` · `director`/`director123`
