package co.com.sofka.mongo.historialasignacionsofkiano.data;

import co.com.sofka.mongo.cliente.ClienteData;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CantidadCambiosAsignacionData {

    @Id
    private ClienteData cliente;

    private Integer cantidadIngresos;
    private Integer cantidadSalidas;
}
