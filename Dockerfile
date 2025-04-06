FROM eclipse-temurin:17-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos necesarios
COPY . .

# Da permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Construye el proyecto sin correr tests
RUN ./mvnw clean package -DskipTests

# Ejecuta el jar generado
CMD ["java", "-jar", "target/apso-0.0.1-SNAPSHOT.jar"]
