package co.com.sofka.model.sofkiano;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Sofkiano {
    private String dni;
    private String nombreCompleto;
}
