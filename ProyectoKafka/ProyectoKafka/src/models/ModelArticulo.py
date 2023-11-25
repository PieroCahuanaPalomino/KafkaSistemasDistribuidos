from .entities.Articulo import Articulo

class ModelArticulo():
    @classmethod
    def listaArticulos(self, db):
        try:
            cursor = db.connection.cursor()
            sql = """SELECT id_articulo, codigo_articulo, nombre_articulo, precio_unitario, cantidad_disponible
            FROM articulo """
            
            cursor.execute(sql)

            articulos = cursor.fetchall()

            if articulos != None: #si se encuentran articulos en la bd
                return articulos
            else:
                return "No se encontraron artículos en la base de datos"

        except Exception as e:
            raise Exception(e)
    
    @classmethod
    def fotoArticulos(self, db):
        try:
            cursor = db.connection.cursor()
            sql = """SELECT  imagen
            FROM articulo """
            
            cursor.execute(sql)

            fotos = cursor.fetchall()

            if fotos != None: #si se encuentran articulos en la bd
                return fotos
            else:
                return "No se encontraron artículos en la base de datos"

        except Exception as e:
            raise Exception(e)