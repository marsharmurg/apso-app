# Modelo de Base de Datos - Aplicación de Sorteos para Clases

## Objetivo del Modelo de Datos
Diseñar una estructura clara, escalable y eficiente para realizar **sorteos grupales entre estudiantes** a partir de una lista cargada vía archivo CSV. Esta estructura permitirá:

- Registrar estudiantes asociados a asignaturas y grupos teóricos.
- Ejecutar sorteos para dividir estudiantes en grupos.
- Guardar y consultar resultados de sorteos previos.
- Prepararse para futuras funcionalidades como autenticación y más tipos de sorteos.

---

## Entidades del Sistema

### 1. Estudiante (`Estudiante`)
Representa a un estudiante cargado desde el archivo CSV.

| Campo           | Tipo      | Descripción                               |
|-----------------|-----------|-------------------------------------------|
| `id`            | Long (PK) | Identificador único                       |
| `nombre`        | String    | Nombre completo del estudiante            |
| `email`         | String    | Correo electrónico (opcional)             |
| `grupo_teorico` | String    | Número o nombre del grupo teórico         |
| `asignatura`    | String    | Código o nombre de la asignatura          |
| `carga_id`      | Long (FK) | Referencia a la carga CSV                 |

---

### 2. Carga de Estudiantes (`CargaEstudiantes`)
Representa una importación de estudiantes desde un archivo CSV.

| Campo           | Tipo           | Descripción                              |
|-----------------|----------------|------------------------------------------|
| `id`            | Long (PK)      | Identificador único                      |
| `nombre_archivo`| String         | Nombre del archivo CSV importado         |
| `fecha_carga`   | Timestamp      | Fecha y hora en que se hizo la carga     |

Relación:  
Una carga puede tener **muchos estudiantes**.

---

### 3. Sorteo Grupal (`SorteoGrupal`)
Registra cada ejecución de sorteo realizada por el tutor o usuario.

| Campo             | Tipo      | Descripción                              |
|-------------------|-----------|------------------------------------------|
| `id`              | Long (PK) | Identificador único                      |
| `fecha`           | Timestamp | Fecha y hora del sorteo                  |
| `nombre_sorteo`   | String    | Nombre o título del sorteo               |
| `cantidad_grupos` | Integer   | Número de grupos definidos               |
| `carga_id`        | Long (FK) | Referencia a la carga de estudiantes     |

Relación:  
Un sorteo pertenece a una sola carga de estudiantes y genera varios grupos.

---

### 4. Grupo (`Grupo`)
Grupos generados como resultado de un sorteo.

| Campo         | Tipo      | Descripción                               |
|---------------|-----------|-------------------------------------------|
| `id`          | Long (PK) | Identificador único del grupo             |
| `numero`      | Integer   | Número del grupo (ej. 1, 2, 3...)          |
| `sorteo_id`   | Long (FK) | Referencia al sorteo al que pertenece     |

Relación:  
Un sorteo puede tener muchos grupos.

---

### 5. EstudianteGrupo (`EstudianteGrupo`)
Tabla intermedia que indica en qué grupo quedó cada estudiante durante un sorteo.

| Campo            | Tipo      | Descripción                               |
|------------------|-----------|-------------------------------------------|
| `id`             | Long (PK) | Identificador                             |
| `estudiante_id`  | Long (FK) | Referencia al estudiante                  |
| `grupo_id`       | Long (FK) | Referencia al grupo                       |

Relación:  
Un grupo tiene muchos estudiantes, y cada estudiante puede haber participado en múltiples sorteos (pero solo en un grupo por sorteo).

---

## Relaciones Clave

- Una **CargaEstudiantes** contiene **muchos Estudiantes**.
- Un **SorteoGrupal** se basa en una **CargaEstudiantes**.
- Un **SorteoGrupal** genera **muchos Grupos**.
- Un **Grupo** contiene **muchos Estudiantes** mediante `EstudianteGrupo`.

---

## Flujo del Proceso

1. **Carga CSV:** El tutor importa estudiantes → se guarda en `CargaEstudiantes` y `Estudiante`.
2. **Sorteo:** Se crea un `SorteoGrupal`, se definen `Grupo`s y se asignan estudiantes aleatoriamente.
3. **Resultados:** Se pueden consultar los resultados del sorteo (grupos generados).

---

## Futuras Extensiones

- Autenticación de usuarios (para historial personal).
- Tipos de sorteo adicionales (ruleta, dados, selección aleatoria).
- Exportación de resultados a PDF o Excel.
- Asignación de roles y comentarios por grupo.

---

## Notas Finales

Este modelo servirá como la base para:
- Crear las **clases entidad con JPA/Hibernate**.
- Diseñar el **schema inicial de PostgreSQL**.
- Establecer los servicios, repositorios y controladores en Spring Boot.
