package co.com.sofka.mongo.historialcambioestadosofkiano.data;

import co.com.sofka.model.commos.enums.TipoMovimiento;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CantidadCambiosEstadoSofkianoData {
    @Id
    private TipoMovimiento tipoMovimiento;
    private Integer cantidadMovimientos;
}
