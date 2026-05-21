# Sistema de Gestión - Turismo Rural (MVP)

Proyecto grupal desarrollado para el curso de **Ingeniería de Software II** de la **Universidad del Quindío**. 

Este MVP (Producto Mínimo Viable) implementa una transacción central de negocio (**Check-in y Pago en Sitio**) conectando múltiples entidades (Reserva, Cabaña, Cupón, Pago) y aplicando reglas de negocio estrictas en el servidor. Adicionalmente, incluye el **Registro Maestro (CRUD)** para la entidad Cliente.

## Equipo de Desarrollo
* Jaider Andrés Melo Rodriguez
* Juliana Andrea Bustamante Niño

## Tecnologías Utilizadas
* **Backend:** Java, Spring Boot, Spring Data JPA.
* **Base de Datos:** H2 (En memoria, autogenerada mediante `data.sql`).
* **Frontend:** HTML5, Tailwind CSS, JavaScript (Fetch API).

---

## Cómo correr el proyecto (Instrucciones)

1. **Clonar o descargar** el repositorio en tu máquina local.
2. **Abrir el proyecto** en tu IDE de preferencia (IntelliJ IDEA, Spring Tool Suite o VS Code).
3. **Ejecutar el Backend:** Localiza y ejecuta la clase principal `QmdApplication.java`, o utiliza la terminal en la raíz del proyecto con el comando:
   ```bash
   ./mvnw spring-boot:run
