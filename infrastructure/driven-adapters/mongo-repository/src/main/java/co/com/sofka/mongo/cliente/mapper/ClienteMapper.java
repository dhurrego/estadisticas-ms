package co.com.sofka.mongo.cliente.mapper;

import co.com.sofka.model.cliente.Cliente;
import co.com.sofka.mongo.cliente.ClienteData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

    public static Cliente toEntity(ClienteData data) {
        return Cliente.builder()
                .nit(data.getNit())
                .razonSocial(data.getRazonSocial())
                .build();
    }
}
