# kafka_message.py
from datetime import datetime

class KafkaMessage:
    def __init__(self, id, date, type, data):
        self.id = id
        # Convertir la fecha de milisegundos a segundos y luego a un objeto datetime
        self.date = datetime.utcfromtimestamp(date / 1000.0)
        self.type = type
        self.data = data

    def __str__(self):
        return f"ID: {self.id}\nDate: {self.date}\nType: {self.type}\nData: {self.data}"
