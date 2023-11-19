# kafka_handler.py
from decimal import Decimal
from confluent_kafka import Consumer, KafkaError , Producer
import json
from datetime import datetime
from kafka_message import KafkaMessage
from sqlalchemy import create_engine, Column, Integer, String, DateTime , Float
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base
import uuid
from datetime import datetime

Base = declarative_base()

class Articulo(Base):
    __tablename__ = 'articulo'
    ID_articulo = Column(Integer, primary_key=True)
    codigo_articulo = Column(String)
    nombre_articulo = Column(String)
    precio_unitario = Column(Float)
    cantidad_disponible = Column(Integer)

engine = create_engine('mysql+mysqlconnector://root:7781@localhost:3306/proyectokafka', echo=True)
Base.metadata.create_all(bind=engine)
Session = sessionmaker(bind=engine)
session = Session()

def update_inventory_and_send_message(codigo_articulo, cantidad_pedido, producer):
    try:
        articulo_db = session.query(Articulo).filter_by(codigo_articulo=codigo_articulo).first()

        if articulo_db:
            # Actualizar la cantidad disponible
            articulo_db.cantidad_disponible -= cantidad_pedido
            session.commit()
            print(f"Se actualizó el inventario para {codigo_articulo}. Cantidad disponible: {articulo_db.cantidad_disponible}")

            # Enviar mensaje a Kafka después de actualizar la base de datos
            send_kafka_message(articulo_db, cantidad_pedido, producer)
    except Exception as e:
        print(f"Error al actualizar el inventario: {e}")

def send_kafka_message(articulo_db, cantidad_pedido, producer):
    try:
        # Construir el mensaje para enviar a Kafka
        kafka_message = {
            "id": str(uuid.uuid4()),
            "date": datetime.utcnow().timestamp() * 1000,  # Convertir la fecha actual a milisegundos
            "type": "LISTO_FACTURA",
            "data": {
                "listaArticulos": [{
                    "codigoArticulo": articulo_db.codigo_articulo,
                    "nombreArticulo": articulo_db.nombre_articulo,
                    "precioUnitario": articulo_db.precio_unitario,
                    "cantidadPedido": cantidad_pedido
                }]
            }
        }

        # Convertir el mensaje a formato JSON
        kafka_message_json = json.dumps(kafka_message)

        # Enviar el mensaje a Kafka
        producer.produce('customers_bill', value=kafka_message_json)
        producer.flush()

        print(f"Mensaje enviado a Kafka: {kafka_message}")

    except Exception as e:
        print(f"Error al enviar el mensaje a Kafka: {e}")

def kafka_consumer():
    consumer_conf = {
        'bootstrap.servers': 'localhost:9092',
        'group.id': 'grupo1',
        'auto.offset.reset': 'earliest'
    }

    producer_conf = {
        'bootstrap.servers': 'localhost:9092'
    }

    consumer = Consumer(consumer_conf)
    producer = Producer(producer_conf)

    consumer.subscribe(['customers_reservation'])

    try:
        while True:
            msg = consumer.poll(1.0)

            if msg is None:
                continue
            elif msg.error():
                if msg.error().code() == KafkaError._PARTITION_EOF:
                    continue
                else:
                    print(msg.error())
                    break
            else:
                mensaje_kafka_dict = json.loads(msg.value())
                mensaje_kafka_obj = KafkaMessage(
                id=mensaje_kafka_dict['id'],
                date=mensaje_kafka_dict['date'],
                type=mensaje_kafka_dict['type'],
                data=mensaje_kafka_dict['data']
                )
                #print(mensaje_kafka_obj)
                print("CARGANDOM1")
                #Procesar la lista de artículos
                for articulo_pedido in mensaje_kafka_obj.data.get('listaArticulos', []):
                    codigo_articulo_pedido = articulo_pedido.get('codigoArticulo')
                    cantidad_pedido = articulo_pedido.get('cantidadPedido')
                    update_inventory_and_send_message(codigo_articulo_pedido, cantidad_pedido, producer)
                continue
    except KeyboardInterrupt:
        pass
    finally:
        consumer.close()
