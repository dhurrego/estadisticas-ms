package co.com.sofka.model.historialasignacionsofkiano;

import co.com.sofka.model.cliente.Cliente;
import co.com.sofka.model.commos.enums.TipoMovimiento;
import co.com.sofka.model.sofkiano.Sofkiano;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class HistorialAsignacionSofkiano {
    private String id;
    private Sofkiano sofkiano;
    private Cliente cliente;
    private TipoMovimiento tipoMovimiento;
    private LocalDateTime fechaMovimiento;
}
