function buscadorDatos(){
console.log('Esta es una función común.');
     $(document).ready(function() {
                $('#tablaEmpresas').DataTable(
                    {"language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.25/i18n/Spanish.json"
                }}
                );
            });
}