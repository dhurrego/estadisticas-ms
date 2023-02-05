package co.com.sofka.model.historialcambioestadosofkiano.dto;

import java.io.Serializable;

public record CantidadCambiosEstadoDTO(
        Integer cantidadIngresos,
        Integer cantidadSalidas
) implements Serializable {
}
