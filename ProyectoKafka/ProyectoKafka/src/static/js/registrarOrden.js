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
        
        let respuesta = "";

        setTimeout(function() {
            // Mostrar el alert después de 10 segundos
            alert('¡Se registro!');
            respuesta = "Exitoso"
            if(respuesta == "Exitoso"){
                alert('Se registro la orden de compra')
            } else if(respuesta == "Insuficiente"){
                alert('No hay stock disponible.')
            } else if(respuesta == "Inexistente"){
                alert('No se encontró el producto')
            }
    
        }, 2000); // 10000 milisegundos = 10 segundos

        

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
            console.log('Orden de compra', datos)
            console.log('Datos enviados al backend:', response);
        })
        .catch(error => {
            console.error('Error al enviar los datos:', error);
        });
    }

    
});

