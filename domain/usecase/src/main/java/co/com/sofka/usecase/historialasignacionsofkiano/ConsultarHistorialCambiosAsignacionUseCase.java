package co.com.sofka.usecase.historialasignacionsofkiano;

import co.com.sofka.model.commos.utils.FechaUtils;
import co.com.sofka.model.historialasignacionsofkiano.dto.CantidadCambiosAsignacionDTO;
import co.com.sofka.model.historialasignacionsofkiano.gateways.HistorialAsignacionSofkianoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RequiredArgsConstructor
public class ConsultarHistorialCambiosAsignacionUseCase {

    private final HistorialAsignacionSofkianoRepository repository;

    public Flux<CantidadCambiosAsignacionDTO> consultarCantidadCambiosAsignacion(LocalDate fechaInicial,
                                                                                 LocalDate fechaFinal) {
        return Mono.just(fechaFinal)
                .doOnNext( fechaFin -> FechaUtils.validarFechaFinal(fechaInicial, fechaFin))
                .flatMapMany(fechaFin -> repository.consultarCantidadCambiosAsignacion(fechaInicial, fechaFin))
                .map( cambiosAsignacion -> new CantidadCambiosAsignacionDTO(cambiosAsignacion.cliente(),
                        cambiosAsignacion.cantidadIngresos(),
                        cambiosAsignacion.cantidadSalidas()));
    }
}
