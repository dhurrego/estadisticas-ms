package co.com.sofka.mongo.sofkiano;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SofkianoData {

    @Field(name = "dni")
    private String dni;

    @Field(name = "nombreCompleto")
    private String nombreCompleto;
}
