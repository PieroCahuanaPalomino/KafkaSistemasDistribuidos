# Utiliza una imagen base de Kafka
FROM confluentinc/cp-kafka:latest

# Expone el puerto 9092 de Kafka
EXPOSE 9092

# Ejecuta el servicio Kafka al iniciar el contenedor
CMD ["kafka-server-start", "/etc/kafka/server.properties"]