class Config:
    SECRET_KEY = 'B!1w8NAt1T^%kvhUI*S^'     #Para manejar datos de sesión

class DevelopmentConfig(Config):
    DEBUG=True                              #Se inicia el servidor en modo de depuración
    #Conexion a la base de datos
    MYSQL_HOST = 'localhost'
    MYSQL_USER = 'root'
    MYSQL_PASSWORD = ''
    MYSQL_DB = 'proyectokafka'

config={
    'development': DevelopmentConfig
}

