from flask import Flask, render_template, request, redirect, url_for, session, jsonify
import json
from config import config
from flask_mysqldb import MySQL
from uuid import uuid4
import hashlib
import base64
import requests

#Modelos
from models.ModelArticulo import ModelArticulo
from models.ModelCuentaCobrar import ModelCuentaCobrar

#Entidades
from models.entities.Articulo import Articulo
from models.entities.CuentaCobrar import CuentaCobrar
app =  Flask(__name__)


db = MySQL(app)

if(db):
    print("Conectado a la bd")
else:
    print("Error de conexion")

@app.route('/')     #redirige a la vista principal
def index():
    return redirect(url_for('opciones'))

@app.route('/opciones')
def opciones():
    
    return render_template('opciones.html')

@app.route('/articulos')
def articulos():
    articulos = ModelArticulo.listaArticulos(db)
    fotos = ModelArticulo.fotoArticulos(db)
    
    fotos_base64 = [base64.b64encode(foto[0]).decode('utf-8') if foto[0] else None for foto in fotos]

    articulos_con_imagenes = []
    for i, articulo in enumerate(articulos):
        articulo_con_imagen = list(articulo)
        articulo_con_imagen.append(fotos_base64[i])
        articulos_con_imagenes.append(articulo_con_imagen)

    return render_template('index.html', articulos = articulos_con_imagenes)

@app.route('/ordenCompra', methods=['GET', 'POST'])
def ordenCompra():
    order_code = hashlib.sha1(str(uuid4()).encode()).hexdigest()[:5]
    lista_articulos = session['lista_articulos']
    importeTotal = session['importeTotal']
    return render_template('ordenCompra.html', lista_articulos=lista_articulos, order_code=order_code, importeTotal=importeTotal)

@app.route('/carrito', methods=['GET', 'POST'])
def carrito():
    if 'lista_articulos' not in session:
        session['lista_articulos'] = []

    if request.method == 'POST':
        data = request.get_json()
        lista_articulos = data.get('articulos')
        importeTotal = data.get('importeTotal')
        print(lista_articulos)
        print(importeTotal)
        session['lista_articulos'] = lista_articulos  # Guarda la lista en la sesión
        session['importeTotal'] = importeTotal  # Guarda la lista en la sesión
    return redirect(url_for('ordenCompra')) 
#REGISTRAR ORDEN
@app.route('/registrarCompra', methods=['GET', 'POST'])
def registrarCompra():
    try:
        orden = request.get_json()
        
        url_local = 'http://localhost:8083/pedido'
        
        # Utiliza el parámetro 'json' para enviar datos JSON en la solicitud POST
        response = requests.post(url_local, json=orden)

        if response.status_code == 200:
            # La solicitud fue exitosa
            print('Contenido de la página:')
            print(response.text)

            # Adaptación de la respuesta según tus necesidades
            return jsonify(response.json())
        else:
            # La solicitud falló
            print(f'Error al realizar la solicitud: {response.status_code} - {response.reason}')
            
            # Adaptación de la respuesta en caso de error
            return jsonify(response.json())
    except Exception as e:
        print(f'Error en la solicitud: {str(e)}')
        return jsonify({"type": "ERROR", "error_message": str(e)})
    
 
    #print(orden)
    #return orden

@app.route('/cuentasCobrar')
def cuentasCobrar():
    clientes = ModelCuentaCobrar.obtenerCliente(db)
    cuentas = ModelCuentaCobrar.listaCuentasCobrar(db)
    print(clientes)
    print(cuentas)
    return render_template('cuentasCobrar.html', clientes=clientes, cuentas=cuentas)

@app.route('/filtrarDatos', methods=['POST'])
def filtrarDatos():
    cliente_seleccionado = request.form['clienteSeleccionado']
    cuentasCliente = ModelCuentaCobrar.cuentasCliente(db, cliente_seleccionado)
    converted_response = [list(item) for item in cuentasCliente]
    json_response=jsonify(converted_response)
    print(json_response)
    return json_response

def statud_404(error):
    return "<h1>Página no encontrada</h1>", 404




if __name__ == '__main__':

    app.config.from_object(config['development']) #Se usa la configuración definida en el archivo config
    app.register_error_handler(404, statud_404)
    app.run()