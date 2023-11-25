class CuentaCobrar():
    def __init__(self, id_cuenta, id_factura, estado_registro, codigo_cliente, nombre_cliente, dni_ruc, total_igv, total_cobrar, fecha_cobro) -> None:
        self.id_cuenta = id_cuenta
        self.id_factura	= id_factura
        self.estado_registro = estado_registro
        self.codigo_cliente = codigo_cliente
        self.nombre_cliente	= nombre_cliente
        self.total_igv = total_igv
        self.total_cobrar =	total_cobrar
        self.fecha_cobro = fecha_cobro