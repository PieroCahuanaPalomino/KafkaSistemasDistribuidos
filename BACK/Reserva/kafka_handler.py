# kafka_handler.py
from confluent_kafka import Consumer, KafkaError
import json
from datetime import datetime
from kafka_message import KafkaMessage
from sqlalchemy import create_engine, Column, Integer, String, DateTime
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class Articulo(Base):
    __tablename__ = 'articulo'
    ID_articulo = Column(Integer, primary_key=True)
    codigo_articulo = Column(String)
    nombre_articulo = Column(String)
    cantidad_disponible = Column(Integer)

engine = create_engine('mysql+mysqlconnector://root:7781@localhost:3306/proyectokafka', echo=True)
Base.metadata.create_all(bind=engine)
Session = sessionmaker(bind=engine)
session = Session()

def update_inventory(codigo_articulo, cantidad_pedido):
    try:
        articulo_db = session.query(Articulo).filter_by(codigo_articulo=codigo_articulo).first()

        if articulo_db:
            # Actualizar la cantidad disponible
            articulo_db.cantidad_disponible -= cantidad_pedido
            session.commit()
            print(f"Se actualizó el inventario para {codigo_articulo}. Cantidad disponible: {articulo_db.cantidad_disponible}")

    except Exception as e:
        print(f"Error al actualizar el inventario: {e}")

def kafka_consumer():
    consumer_conf = {
        'bootstrap.servers': 'localhost:9092',
        'group.id': 'grupo1',
        'auto.offset.reset': 'earliest'
    }

    consumer = Consumer(consumer_conf)
    consumer.subscribe(['customers_reservation'])

    try:
        while True:
            msg = consumer.poll(1.0)

            if msg is None:
                continue
            if msg.error():
                if msg.error().code() == KafkaError._PARTITION_EOF:
                    continue
                else:
                    print(msg.error())
                    break

            mensaje_kafka_dict = json.loads(msg.value())
            mensaje_kafka_obj = KafkaMessage(
                id=mensaje_kafka_dict['id'],
                date=mensaje_kafka_dict['date'],
                type=mensaje_kafka_dict['type'],
                data=mensaje_kafka_dict['data']
            )

            print(mensaje_kafka_obj)

            # Procesar la lista de artículos
            for articulo_pedido in mensaje_kafka_obj.data.get('listaArticulos', []):
                codigo_articulo_pedido = articulo_pedido.get('codigoArticulo')
                cantidad_pedido = articulo_pedido.get('cantidadPedido')

                # Actualizar el inventario en la base de datos
                update_inventory(codigo_articulo_pedido, cantidad_pedido)

    except KeyboardInterrupt:
        pass
    finally:
        consumer.close()
