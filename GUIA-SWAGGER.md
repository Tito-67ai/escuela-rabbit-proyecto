# Cómo probar todo el sistema desde Swagger

Guía rápida para demostrar el funcionamiento completo usando **solo Swagger UI**.

---

## 1. Swagger de cada servicio

| Servicio | URL |
| --- | --- |
| auth-service | http://localhost:8084/swagger-ui/index.html |
| admin-service | http://localhost:8083/swagger-ui/index.html |
| alumno-service | http://localhost:8081/swagger-ui/index.html |
| curso-service | http://localhost:8082/swagger-ui/index.html |
| config-server | http://localhost:8888/swagger-ui/index.html |
| gateway-service | http://localhost:8080/webjars/swagger-ui/index.html |

---

## 2. Loguearse y obtener el token

En **cualquier** Swagger, `POST /auth/login` (es público, no necesita autorización):

```json
{
  "username": "admin",
  "password": "admin777"
}
```

Respuesta (200):

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9....",
  "refreshToken": "....",
  "nombreUsuario": "admin",
  "rol": "ROOT"
}
```

Copiar el valor de **`token`**.

---

## 3. Autorizar en Swagger (botón Authorize)

1. Clic en **Authorize** (arriba a la derecha).
2. En el campo **Value** pegar **solo el token** (sin `Bearer`, Swagger lo agrega solo).
3. Clic en **Authorize** → **Close**.
4. El diálogo queda en `Authorized`. Ahora los endpoints protegidos se pueden ejecutar.

> El **admin (ROOT)** puede hacer todo. Cada pestaña de Swagger tiene su propia autorización: hay que repetir el Authorize en cada servicio.
> El token vence en **24 h**; si da 401, volver al paso 2.

---

## 4. Recorrido completo (usando admin / ROOT)

Usar siempre el botón **Try it out** → editar el JSON → **Execute**.

### 4.1 Registrarse / crear un usuario (auth-service, `POST /auth/register`)

**Es público (auto-registro):** no hace falta estar logueado ni autorizar.

```json
{
  "username": "profe01",
  "password": "profe1234",
  "correo": "profe01@escuela.com",
  "rol": "DOCENTE"
}
```

Respuesta: `201` → `Usuario registrado con exito`.

**Regla de roles:** si te registrás **sin ser `ROOT`**, el único rol permitido es `DOCENTE`. Si pedís otro (por ejemplo `ADMINISTRATIVO`), responde **`403`** con el mensaje *"No podes asignar el rol ... solo un usuario ROOT puede hacerlo"*. Para dar de alta `ADMINISTRATIVO`, `PRECEPTOR`, `DIRECTOR` o `ROOT` hay que estar autenticado como **ROOT** (paso 3: Authorize con el token de `admin/admin777`).
Roles válidos: `ROOT`, `PRECEPTOR`, `DOCENTE`, `DIRECTOR`, `ADMINISTRATIVO`.
Después iniciás sesión con ese usuario (`POST /auth/login`).

### 4.2 Crear personal (admin-service, `POST /admin/personal`)

```json
{
  "nombre": "Ana",
  "apellido": "Gomez",
  "dni": "22222222",
  "sueldo": 150000,
  "cargo": "Profesora de Matematica",
  "tipo": "DOCENTE"
}
```

`tipo` puede ser: `DOCENTE`, `ADMINISTRATIVO` o `DIRECTIVO`.
Anotar el `id` que devuelve (será el `docenteId` del curso).

### 4.3 Crear un curso

> Importante: primero crear el **personal (docente)** y **usar su `id`** como `docenteId`.

En Swagger de **curso-service**, `POST /cursos`:

```json
{
  "nombre": "Matemática 1A",
  "docenteId": 1,
  "cupo": 30,
  "materia": "Matemática",
  "horario": "Lunes 08:00-10:00"
}
```

### 4.4 Crear un alumno

En Swagger de **alumno-service**, `POST /alumnos` (usar el `id` del curso):

```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "dni": "12345678",
  "cursoId": 1
}
```

### 4.5 Listar / editar / borrar

| Acción | Endpoint |
| --- | --- |
| Listar | `GET /alumnos`, `GET /cursos`, `GET /admin/personal` |
| Traer uno | `GET /alumnos/{id}`, `GET /cursos/{id}` |
| Editar | `PUT /alumnos/{id}`, `PUT /cursos/{id}` |
| Activar | `PUT /alumnos/{id}/activar` |
| Borrar (baja lógica) | `DELETE /alumnos/{id}`, `DELETE /cursos/{id}` |

---

## 5. Roles y permisos

| Operación | Rol necesario |
| --- | --- |
| Login / Refresh | público |
| Registrarse (`/auth/register`) | público · sin ROOT solo `DOCENTE` (con ROOT, cualquier rol) |
| Personal / `/admin/personal` | `ROOT`, `ADMINISTRATIVO` |
| Cursos / `/cursos` | `ROOT`, `PRECEPTOR` |
| Alumnos / `/alumnos` | `PRECEPTOR` |

Usuarios de ejemplo (se recrean al reiniciar auth-service):

| Usuario | Contraseña | Rol |
| --- | --- | --- |
| admin | admin777 | ROOT |
| preceptor | preceptor123 | PRECEPTOR |
| administrativo | administrativo123 | ADMINISTRATIVO |
| docente | docente123 | DOCENTE |
| director | director123 | DIRECTOR |

---

## 6. Notas

- La auth usa **H2 en memoria**: al reiniciar el contenedor `auth-service` se pierden los usuarios creados (vuelven solo los 5 de ejemplo). Lo mismo aplica a alumnos/cursos/personal.
- El gateway (`http://localhost:8080`) enruta por Eureka a los mismos endpoints (`/auth`, `/alumnos`, `/cursos`, `/admin`), útil para probar igual que entra el frontend.
- Si un endpoint devuelve **401** → no autorizaste, el token venció, o lo pegaste mal en **Authorize** (por ejemplo con `Bearer` de más). Respuesta clara: `{"status":401,"message":"Token invalido o expirado. Volve a iniciar sesion."}`. El **login** y el **refresh** no se bloquean por un token viejo, así siempre podés volver a entrar.
- Si `POST /auth/register` devuelve **403** → pediste un rol distinto a `DOCENTE` sin estar autenticado como ROOT. Autorizá con el token de `admin/admin777` (paso 2 y 3) y volvé a intentar.
