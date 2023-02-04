package co.com.sofka.model.historialasignacionsofkiano.dto;

import co.com.sofka.model.cliente.Cliente;
import co.com.sofka.model.commos.enums.TipoMovimiento;
import co.com.sofka.model.sofkiano.Sofkiano;

import java.io.Serializable;

public record AgregarHistorialAsignacionSofkianoDTO(
        Sofkiano sofkiano,
        Cliente cliente,
        TipoMovimiento tipoMovimiento
) implements Serializable {
}
