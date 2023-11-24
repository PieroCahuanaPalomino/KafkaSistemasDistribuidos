from confluent_kafka import Producer
import json

# Configura la conexión con el broker de Kafka
conf = {
    'bootstrap.servers': 'localhost:9092',  # Reemplaza con la lista de brokers de Kafka
}

# Crea el productor
producer = Producer(conf)

# Define el mensaje en formato JSON
mensaje = {
    "id": "f3b79487-65fd-4f37-8bac-f365ac285f83",
    "date": 1700625504478,
    "type": "LISTO_FACTURA",
    "data": {
        "listaArticulos": [
            {"idArticulo": 1,"codigoArticulo": "A325OP", "nombreArticulo": "Soporte de Laptop", "precioUnitario": 82.500, "cantidadPedido": 1},
            {"idArticulo": 2, "codigoArticulo": "A325OV","nombreArticulo": "Mouse Ergonómico", "precioUnitario": 198.950, "cantidadPedido": 1}
        ],
        "comprador": {"nombre": "Piero Raul Cahuana Palomino", "dniRuc": "77810931", "ordenCompra": "001"}
    }
}

# Convierte el mensaje a formato JSON
mensaje_json = json.dumps(mensaje)

# Envia el mensaje al topic especificado
topic = 'customers_bill'  # Reemplaza con el nombre de tu topic
producer.produce(topic, key=None, value=mensaje_json)

# Espera a que los mensajes se envíen (puedes ajustar el tiempo según tus necesidades)
producer.flush(5)
