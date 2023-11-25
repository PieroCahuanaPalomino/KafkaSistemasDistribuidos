from .entities.CuentaCobrar import CuentaCobrar

class ModelCuentaCobrar():
    @classmethod
    def listaCuentasCobrar(self, db):
        try:
            cursor = db.connection.cursor()
            sql = """SELECT id_cuenta, id_factura, estado_registro, codigo_cliente, nombre_cliente, dni_ruc, total_igv, total_cobrar, fecha_cobro
            FROM cuentaporcobrar """
            
            cursor.execute(sql)

            cuentas = cursor.fetchall()

            if cuentas != None: #si se encuentran articulos en la bd
                return cuentas
            else:
                return "No se encontraron artículos en la base de datos"

        except Exception as e:
            raise Exception(e)
        
    @classmethod
    def cuentasCliente(self, db, dni_ruc):
        try:
            cursor = db.connection.cursor()
            sql = """SELECT id_cuenta, id_factura, estado_registro, codigo_cliente, nombre_cliente, dni_ruc, total_igv, total_cobrar, DATE_FORMAT(fecha_cobro, '%%Y-%%m-%%d %%H:%%i:%%s')
            FROM cuentaporcobrar WHERE dni_ruc = %s""".format(dni_ruc)
            
            cursor.execute(sql, (dni_ruc,))

            cuentas = cursor.fetchall()

            if cuentas != None: #si se encuentran articulos en la bd
                return cuentas
            else:
                return "No se encontraron artículos en la base de datos"

        except Exception as e:
            raise Exception(e)
        
    @classmethod
    def obtenerCliente(self, db):
        try:
            cursor = db.connection.cursor()
            sql = """SELECT DISTINCT nombre_cliente, dni_ruc
            FROM cuentaporcobrar """
            
            cursor.execute(sql)

            clientes = cursor.fetchall()

            if clientes != None: #si se encuentran articulos en la bd
                return clientes
            else:
                return "No se encontraron artículos en la base de datos"

        except Exception as e:
            raise Exception(e)