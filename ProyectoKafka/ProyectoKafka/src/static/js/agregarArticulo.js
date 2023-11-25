let listaArticulos = [];
document.addEventListener('DOMContentLoaded', function() {
    const botonesAñadir = document.querySelectorAll('.botonAñadir');

    botonesAñadir.forEach((boton) => {
        boton.addEventListener('click', (event) => {
            event.preventDefault(); // Evita el comportamiento por defecto del formulario

            const idArticulo = boton.dataset.id;
            const cantidadInput = document.getElementById(`cantidad${idArticulo}`);
            const cantidad = cantidadInput.value;
            const codigo = boton.dataset.codigo;
            const nombre = boton.dataset.nombre;
            const precio = boton.dataset.precio;
            
            agregarArticulo(idArticulo, codigo, nombre, precio, cantidad);
        });
    });


    function agregarArticulo(id, codigo, nombre, precio, cantidad) {
        const importe = (cantidad * parseFloat(precio)).toFixed(3);
        const articulo = {
            idArticulo: id,
            codigoArticulo: codigo,
            nombreArticulo: nombre,
            precioUnitario: precio,
            cantidadPedido: cantidad,
            importe: importe
        };

        listaArticulos.push(articulo);
        console.log('Artículo agregado:', articulo);
        console.log('Lista de artículos:', listaArticulos);
    }
});



document.addEventListener('DOMContentLoaded', function() {
    const botonCarritos = document.getElementById('botonCarritos');

    botonCarritos.addEventListener('click', function() {
        enviarArticulosAlBackend();
    });

    function enviarArticulosAlBackend() {
        const importeTotal = listaArticulos.reduce((total, articulo) => {
            return total + parseFloat(articulo.importe);
        }, 0);
        fetch('/carrito', {
            method: 'POST',
            body: JSON.stringify({ articulos: listaArticulos,  importeTotal: importeTotal }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
        
            console.log('Respuesta del backend:', response);
            if (response.ok) {
                window.location.href = '/carrito'; // Redirige a la página ordenCompra.html
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
});