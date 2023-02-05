package co.com.sofka.mongo.historialcambioestadosofkiano;

import co.com.sofka.model.historialcambioestadosofkiano.CantidadCambiosEstadoSofkiano;
import co.com.sofka.model.historialcambioestadosofkiano.HistorialCambiosEstadoSofkiano;
import co.com.sofka.model.commos.enums.TipoMovimiento;
import co.com.sofka.model.historialcambioestadosofkiano.gateways.HistorialCambiosEstadoSofkianoRepository;
import co.com.sofka.mongo.helper.AdapterOperations;
import co.com.sofka.mongo.historialcambioestadosofkiano.data.CantidadCambiosEstadoSofkianoData;
import co.com.sofka.mongo.historialcambioestadosofkiano.data.HistorialCambiosEstadoSofkianoData;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static co.com.sofka.model.exception.tecnico.TechnicalException.Tipo.ERROR_COMUNICACION_BASE_DATOS;

@Slf4j
@Repository
public class HistorialCambiosEstadoSofkianoRepositoryAdapter extends AdapterOperations<HistorialCambiosEstadoSofkiano,
        HistorialCambiosEstadoSofkianoData, String, HistorialCambiosEstadoSofkianoDataRepository>
        implements HistorialCambiosEstadoSofkianoRepository
{

    public HistorialCambiosEstadoSofkianoRepositoryAdapter(HistorialCambiosEstadoSofkianoDataRepository repository,
                                                           ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d,
                HistorialCambiosEstadoSofkiano.HistorialCambiosEstadoSofkianoBuilder.class).build());
    }

    @Override
    public Mono<CantidadCambiosEstadoSofkiano> consultarCantidadCambiosEstados(LocalDate fechaInicial, LocalDate fechaFinal) {
        return repository.consultarCantidadCambiosEstado(fechaInicial, fechaFinal.plusDays(1))
                .collectList()
                .map(ingresosSalidas -> {
                    final Integer ingresos = ingresosSalidas.stream()
                            .filter( ingresoSalida -> TipoMovimiento.INGRESO.name()
                                    .equals(ingresoSalida.getTipoMovimiento().name()))
                            .mapToInt(CantidadCambiosEstadoSofkianoData::getCantidadMovimientos)
                            .sum();


                    final Integer salidas = ingresosSalidas.stream()
                            .filter( ingresoSalida -> TipoMovimiento.SALIDA.name()
                                    .equals(ingresoSalida.getTipoMovimiento().name()))
                            .mapToInt(CantidadCambiosEstadoSofkianoData::getCantidadMovimientos)
                            .sum();

                    return new CantidadCambiosEstadoSofkiano(ingresos, salidas);
                }).onErrorResume(throwable -> {
                    log.error(throwable.toString());
                    return Mono.error(ERROR_COMUNICACION_BASE_DATOS::build);
                });
    }
}
