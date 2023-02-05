package co.com.sofka.mongo.cliente;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClienteData {
    @Field(name = "nit")
    private String nit;

    @Field(name = "razonSocial")
    private String razonSocial;
}
