# Sistema de Gestión Escolar - Frontend

Interfaz web cliente desarrollada con Angular que consume la API del sistema distribuido a través del Gateway.

## Tecnologías
- Framework: Angular
- Servidor WEB (Contenedor): Nginx (Alpine)
- Consumo de API: Gateway Centralizado (http://localhost:8080)

## Despliegue con Docker (Linux / Ubuntu VM)

1. Clonar el repositorio en la Máquina Virtual:
   git clone https://github.com/48115220-sketch/sistema-gestion-escolar-frontend.git
   cd sistema-gestion-escolar-frontend

2. Levantar el contenedor Nginx en la VM:
   docker compose up --build

3. Abrir en el navegador:
   http://localhost:4200

## Ejecución Local para Desarrollo (Sin Docker)
ng serve --open
