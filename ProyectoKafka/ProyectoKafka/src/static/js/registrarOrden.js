document.addEventListener('DOMContentLoaded', function() {
    const botonRegistrarOrden = document.getElementById('btn-registrar');

    botonRegistrarOrden.addEventListener('click', function(event) {
        event.preventDefault();

        // Obtener datos del formulario
        const nombreRazonSocial = document.getElementById('nombre_razon_social').value;
        const dniRuc = document.getElementById('dni_ruc').value;
        const codigoCompra = document.getElementById('codigoCompra').value;
        
        const listaArticulos = JSON.parse(botonRegistrarOrden.getAttribute('data-articulos'));
        // Crear objeto con los datos del formulario y la lista de artículos
        const listaArticulosEnvio = listaArticulos.map(articulo => {
            return {
                codigoArticulo: articulo.codigoArticulo,
                nombreArticulo: articulo.nombreArticulo,
                precioUnitario: parseFloat(articulo.precioUnitario),
                cantidadPedido: parseInt(articulo.cantidadPedido)
            };
        });
        const comprador = {
            nombre: nombreRazonSocial,
            dniRuc: dniRuc,
            ordenCompra: codigoCompra
        };

        const datosOrden = {
            listaArticulos: listaArticulosEnvio,
            comprador: comprador,
        };

        enviarDatosAlBackend(datosOrden); 

    });

    function enviarDatosAlBackend(datos) {
        fetch('/registrarCompra', {
            method: 'POST',
            body: JSON.stringify(datos),
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                // La solicitud fue exitosa (código de estado 2xx)
                return response.json(); // Devolver los datos JSON de la respuesta
            } else {
                // La solicitud falló (código de estado no es 2xx)
                throw new Error(`Error en la solicitud: ${response.status} - ${response.statusText}`);
            }
        })
        .then(data => {
            // Manejar los datos recibidos del backend
            console.log('Datos recibidos del backend:', data);
        
            // Acceder a la propiedad 'type' en los datos
            const responseType = data.type;
        
            // Realizar acciones según el tipo de respuesta
            switch (responseType) {
                case 'EXITO':
                    alert('¡Se registró correctamente!');
                    break;
                case 'INSUFICIENTE':
                    alert('Error: Cantidad insuficiente.');
                    break;
                case 'INEXISTENTE':
                    alert('Error: Elemento inexistente.');
                    break;
                case 'ERROR':
                    alert('Error general en la solicitud.');
                    break;
                default:
                    alert('Respuesta no reconocida.');
                    break;
            }
        })
        
        .catch(error => {
            // Manejar los errores de la solicitud
            console.error('Error al enviar los datos:', error.message);
            // Mostrar un alert con la descripción del problema en caso de error
            alert(`Hubo un error al registrar la orden: ${error.message}`);
        });
    }
    
});

