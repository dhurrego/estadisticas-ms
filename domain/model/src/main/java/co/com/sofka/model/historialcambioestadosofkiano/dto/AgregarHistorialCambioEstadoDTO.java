package co.com.sofka.model.historialcambioestadosofkiano.dto;

import co.com.sofka.model.commos.enums.TipoMovimiento;
import co.com.sofka.model.sofkiano.Sofkiano;

import java.io.Serializable;

public record AgregarHistorialCambioEstadoDTO(
        Sofkiano sofkiano,
        TipoMovimiento tipoMovimiento
) implements Serializable {
}
