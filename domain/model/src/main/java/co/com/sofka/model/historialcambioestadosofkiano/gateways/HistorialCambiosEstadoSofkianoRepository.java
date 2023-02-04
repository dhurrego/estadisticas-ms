package co.com.sofka.model.historialcambioestadosofkiano.gateways;

import co.com.sofka.model.historialcambioestadosofkiano.CantidadCambiosEstadoSofkiano;
import co.com.sofka.model.historialcambioestadosofkiano.HistorialCambiosEstadoSofkiano;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface HistorialCambiosEstadoSofkianoRepository {
    Mono<CantidadCambiosEstadoSofkiano> consultarCantidadCambiosEstados(LocalDate fechaInicial, LocalDate fechaFinal);
    Mono<HistorialCambiosEstadoSofkiano> save(HistorialCambiosEstadoSofkiano historialCambiosEstadoSofkiano);
}
