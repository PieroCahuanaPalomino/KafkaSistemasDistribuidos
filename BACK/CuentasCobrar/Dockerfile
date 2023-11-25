# Suponiendo que tu aplicación requiere Java para ejecutarse
FROM openjdk:17

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo JAR al contenedor
COPY target/*.jar CuentasCobrar-1.0.0.jar

# Comando de entrada para ejecutar la aplicación al iniciar el contenedor
CMD ["java", "-jar", "CuentasCobrar-1.0.0.jar"]
