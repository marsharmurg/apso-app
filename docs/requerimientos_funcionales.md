**Requerimientos Funcionales - APSO: Aplicación de Sorteos para Clases**

### **1. Descripción General**
La aplicación permite a los tutores realizar sorteos y agrupaciones de estudiantes dentro de los grupos teóricos de clase, con diferentes modalidades. Además, ofrece opciones como ruleta para participaciones y lanzamiento de dados.

### **2. Actores del Sistema**
- **Tutor/Docente:** Usuario que organiza los sorteos y agrupa a los estudiantes.
- **Estudiante:** Participante en los sorteos.
- **Usuario anónimo:** Puede usar la aplicación sin autenticación, pero no guarda historial.
- **Usuario autenticado:** Puede almacenar su historial de sorteos.

### **3. Funcionalidades Principales**
#### **3.1. Gestión de Estudiantes**
- Registrar estudiantes manualmente o mediante importación de un archivo CSV (valores separados por comas).
- Editar y eliminar estudiantes.
- Visualizar la lista de estudiantes registrados.

#### **3.2. Creación de Sorteos**
- Generar sorteos de manera aleatoria.
- Modalidades de sorteo:
  - **Sorteo aleatorio:** Selección al azar de un estudiante.
  - **Agrupación por cantidad:** Dividir la clase en grupos de tamaño definido.
  - **Ruleta:** Interfaz gráfica para seleccionar estudiantes.
  - **Lanzamiento de dados:** Generar valores aleatorios en base a un dado virtual.

#### **3.3. Historial de Sorteos**
- Almacenar sorteos realizados (solo para usuarios autenticados).
- Consultar sorteos previos.
- Eliminar registros de sorteos guardados.

#### **3.4. Autenticación (Opcional)**
- Permitir el uso sin autenticación.
- Opción de autenticarse con correo y contraseña.
- Opción de iniciar sesión con Google (OAuth, previsión futura).

#### **3.5. Interfaz de Usuario**
- Interfaz web accesible desde cualquier dispositivo.
- Diseño responsivo y amigable.

### **4. Restricciones y Condiciones**
- Un sorteo no puede realizarse si no hay estudiantes registrados.
- En la modalidad "Agrupación por cantidad", si el número de estudiantes no es divisible, algunos grupos pueden tener más integrantes.
- Un usuario anónimo no puede acceder al historial.

### **5. Requerimientos Adicionales**
- Registro de logs para depuración y seguimiento de eventos.
- Configuración de base de datos con PostgreSQL.
- Creación de la base de datos de forma automática en desarrollo.
