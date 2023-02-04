package co.com.sofka.model.historialasignacionsofkiano.dto;

import co.com.sofka.model.cliente.Cliente;

import java.io.Serializable;

public record CantidadCambiosAsignacionDTO(
        Cliente cliente,
        Integer cantidadIngresos,
        Integer cantidadSalidas
) implements Serializable {
}
