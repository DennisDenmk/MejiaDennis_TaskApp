# TaskApp - Administrador de Tareas

Una aplicación Android nativa construida en **Java** para gestionar tareas mediante una API REST personalizada.

## 🚀 Características

- **Autenticación completa**: Registro e inicio de sesión de usuarios.
- **Gestión de Tareas (CRUD)**:
  - Listar tareas personales.
  - Crear nuevas tareas con título.
  - Editar el título de tareas existentes.
  - Eliminar tareas.
  -
## 🔗 Integración con la API

La aplicación se conecta a `https://taskcrud-rd1k.onrender.com/`. Siguiendo la documentación de la API, todos los endpoints (excepto registro) son de tipo `POST` y requieren las credenciales en el cuerpo del JSON.

### Endpoints implementados:
- `POST /register`: Registro de nuevos usuarios.
- `POST /login`: Validación de credenciales.
- `POST /tasks/list`: Obtención de la lista de tareas del usuario.
- `POST /tasks`: Creación de una nueva tarea.
- `POST /tasks/{id}/update`: Actualización del estado o título de una tarea.
- `POST /tasks/{id}/delete`: Eliminación de una tarea.

## 🏗️ Estructura del Proyecto

- `com.example.taskapp.data`: Contiene los modelos de datos (POJOs), la interfaz de la API, el cliente Retrofit y el repositorio.
- `com.example.taskapp.ui`: Contiene los fragmentos (Login, Register, Tasks), los ViewModels y el adaptador del RecyclerView.
- `com.example.taskapp.MainActivity`: Actividad principal que aloja el grafo de navegación.
##  Funcionamiento

## Registro de usuario
<img width="313" height="556" alt="image" src="https://github.com/user-attachments/assets/bebc5c2b-261f-44ee-9562-616a795ba9da" />
## CRUD
<img width="306" height="565" alt="image" src="https://github.com/user-attachments/assets/a86fabc8-ff12-4a83-a783-71fb32bcd387" />
## CREATE
<img width="412" height="587" alt="image" src="https://github.com/user-attachments/assets/92c2ab01-da07-4205-b228-484391274fbe" />
<img width="420" height="366" alt="image" src="https://github.com/user-attachments/assets/77a72e71-a1a4-4c11-b293-ed31cbea2a57" />
## UPDATE
<img width="392" height="548" alt="image" src="https://github.com/user-attachments/assets/70249c54-0fd4-4063-b9ee-79baa4c78b9d" />
<img width="427" height="368" alt="image" src="https://github.com/user-attachments/assets/e62fa81b-fae7-4d9c-9f1c-86a0ca4cd5b9" />
## DELETE
<img width="392" height="362" alt="image" src="https://github.com/user-attachments/assets/18ca510d-fe58-41ee-b069-8e9d06748da9" />



