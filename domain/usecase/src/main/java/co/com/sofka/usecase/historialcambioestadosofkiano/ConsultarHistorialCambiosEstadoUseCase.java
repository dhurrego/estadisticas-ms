package co.com.sofka.usecase.historialcambioestadosofkiano;

import co.com.sofka.model.commos.utils.FechaUtils;
import co.com.sofka.model.historialcambioestadosofkiano.dto.CantidadCambiosEstadoDTO;
import co.com.sofka.model.historialcambioestadosofkiano.gateways.HistorialCambiosEstadoSofkianoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RequiredArgsConstructor
public class ConsultarHistorialCambiosEstadoUseCase {

    private final HistorialCambiosEstadoSofkianoRepository repository;

    public Mono<CantidadCambiosEstadoDTO> consultarCantidadCambiosEstado(LocalDate fechaInicial,
                                                                         LocalDate fechaFinal) {
        return Mono.just(fechaFinal)
                .doOnNext( fechaFin -> FechaUtils.validarFechaFinal(fechaInicial, fechaFin))
                .flatMap(fechaFin -> repository.consultarCantidadCambiosEstados(fechaInicial, fechaFin))
                .map( ingresosSalidas -> new CantidadCambiosEstadoDTO(ingresosSalidas.cantidadIngresos(),
                        ingresosSalidas.cantidadSalidas()));
    }
}
