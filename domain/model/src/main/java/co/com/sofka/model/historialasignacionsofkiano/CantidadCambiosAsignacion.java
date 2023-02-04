package co.com.sofka.model.historialasignacionsofkiano;

import co.com.sofka.model.cliente.Cliente;

public record CantidadCambiosAsignacion(
        Cliente cliente,
        Integer cantidadIngresos,
        Integer cantidadSalidas
) {
}
