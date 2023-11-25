class Articulo():
    def __init__(self, id_articulo, codigo_articulo, nombre_articulo, precio_unitario, cantidad_disponible, imagen) -> None:
        self.id_articulo = id_articulo
        self.codigo_articulo = codigo_articulo
        self.nombre_articulo = nombre_articulo
        self.precio_unitario = precio_unitario
        self.cantidad_disponible = cantidad_disponible
        self.imagen = imagen