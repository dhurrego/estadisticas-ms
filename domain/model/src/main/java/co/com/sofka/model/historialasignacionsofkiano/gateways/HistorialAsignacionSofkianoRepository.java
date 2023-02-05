package co.com.sofka.model.historialasignacionsofkiano.gateways;

import co.com.sofka.model.historialasignacionsofkiano.CantidadCambiosAsignacion;
import co.com.sofka.model.historialasignacionsofkiano.HistorialAsignacionSofkiano;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface HistorialAsignacionSofkianoRepository {

    Flux<CantidadCambiosAsignacion> consultarCantidadCambiosAsignacion(LocalDate fechaInicial, LocalDate fechaFinal);
    Mono<HistorialAsignacionSofkiano> save(HistorialAsignacionSofkiano historialAsignacionSofkiano);
}
