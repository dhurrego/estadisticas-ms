package co.com.sofka.mongo.historialasignacionsofkiano;

import co.com.sofka.model.cliente.Cliente;
import co.com.sofka.model.historialasignacionsofkiano.CantidadCambiosAsignacion;
import co.com.sofka.model.historialasignacionsofkiano.HistorialAsignacionSofkiano;
import co.com.sofka.model.historialasignacionsofkiano.gateways.HistorialAsignacionSofkianoRepository;
import co.com.sofka.mongo.cliente.mapper.ClienteMapper;
import co.com.sofka.mongo.helper.AdapterOperations;
import co.com.sofka.mongo.historialasignacionsofkiano.data.HistorialAsignacionSofkianoData;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static co.com.sofka.model.exception.tecnico.TechnicalException.Tipo.ERROR_COMUNICACION_BASE_DATOS;

@Slf4j
@Repository
public class HistorialAsignacionSofkianoRepositoryAdapter extends AdapterOperations<HistorialAsignacionSofkiano,
        HistorialAsignacionSofkianoData, String, HistorialAsignacionSofkianoDataRepository>
        implements HistorialAsignacionSofkianoRepository
{

    public HistorialAsignacionSofkianoRepositoryAdapter(HistorialAsignacionSofkianoDataRepository repository,
                                                        ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d,
                HistorialAsignacionSofkiano.HistorialAsignacionSofkianoBuilder.class).build());
    }

    @Override
    public Flux<CantidadCambiosAsignacion> consultarCantidadCambiosAsignacion(LocalDate fechaInicial, LocalDate fechaFinal) {
        return repository.consultarCantidadCambiosAsignacion(fechaInicial, fechaFinal.plusDays(1))
                .map(cantidadCambiosAsignacionData -> {
                    Cliente cliente = ClienteMapper.toEntity(cantidadCambiosAsignacionData.getCliente());
                    return new CantidadCambiosAsignacion(
                            cliente,
                            cantidadCambiosAsignacionData.getCantidadIngresos(),
                            cantidadCambiosAsignacionData.getCantidadSalidas()
                    );
                })
                .onErrorResume(throwable -> {
                    log.error(throwable.toString());
                    return Mono.error(ERROR_COMUNICACION_BASE_DATOS::build);
                });
    }
}
