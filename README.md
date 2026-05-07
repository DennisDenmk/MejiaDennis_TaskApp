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
