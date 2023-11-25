# Usa una imagen base de OpenJDK 17
# Usa una imagen base de OpenJDK 17
FROM openjdk:17

# Mantenedor del Dockerfile
MAINTAINER distribuidos

# Crear un grupo y un usuario para la aplicación
RUN groupadd -r spring && useradd -r -g spring spring

# Establecer el usuario por defecto
USER spring:spring

# Argumento para especificar el archivo JAR
ARG JAR_FILE=target/*.jar

# Copiar el archivo JAR al contenedor con el nombre deseado
COPY ${JAR_FILE} AdministracionInventario-1.0.0.jar

# Comando de entrada para ejecutar la aplicación al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "/AdministracionInventario-1.0.0.jar"]
