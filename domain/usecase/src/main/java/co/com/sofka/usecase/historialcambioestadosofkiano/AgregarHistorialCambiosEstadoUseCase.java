package co.com.sofka.usecase.historialcambioestadosofkiano;

import co.com.sofka.model.historialcambioestadosofkiano.dto.AgregarHistorialCambioEstadoDTO;
import co.com.sofka.model.historialcambioestadosofkiano.factoria.HistorialCambiosEstadoSofkianoFactory;
import co.com.sofka.model.historialcambioestadosofkiano.gateways.HistorialCambiosEstadoSofkianoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AgregarHistorialCambiosEstadoUseCase {

    private final HistorialCambiosEstadoSofkianoRepository repository;

    public Mono<Boolean> agregarHistorialCambiosEstado(
            AgregarHistorialCambioEstadoDTO agregarHistorialCambioEstadoDTO) {
        return Mono.just(agregarHistorialCambioEstadoDTO)
                .map(HistorialCambiosEstadoSofkianoFactory::crearHistorialCambioEstado)
                .flatMap(repository::save)
                .map( historial -> Boolean.TRUE);
    }
}
