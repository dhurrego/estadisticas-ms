package co.com.sofka.mongo.historialasignacionsofkiano.data;

import co.com.sofka.model.commos.enums.TipoMovimiento;
import co.com.sofka.mongo.cliente.ClienteData;
import co.com.sofka.mongo.sofkiano.SofkianoData;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "historial_asignaciones_sofkianos")
public class HistorialAsignacionSofkianoData {

    @Id
    private String id;

    @Field(name = "sofkiano")
    private SofkianoData sofkiano;

    @Field(name = "cliente")
    private ClienteData cliente;

    @Field(name = "tipoMovimiento")
    private TipoMovimiento tipoMovimiento;

    @Field(name = "fechaMovimiento")
    private LocalDateTime fechaMovimiento;
}
