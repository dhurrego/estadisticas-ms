package co.com.sofka.usecase.historialasignacionsofkiano;

import co.com.sofka.model.historialasignacionsofkiano.dto.AgregarHistorialAsignacionSofkianoDTO;
import co.com.sofka.model.historialasignacionsofkiano.factoria.HistorialAsignacionSofkianoFactory;
import co.com.sofka.model.historialasignacionsofkiano.gateways.HistorialAsignacionSofkianoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AgregarHistorialAsignacionSofkianoUseCase {

    private final HistorialAsignacionSofkianoRepository repository;

    public Mono<Boolean> agregarHistorialCambiosAsignacion(
            AgregarHistorialAsignacionSofkianoDTO agregarHistorialAsignacionSofkianoDTO) {
        return Mono.just(agregarHistorialAsignacionSofkianoDTO)
                .map(HistorialAsignacionSofkianoFactory::crearHistorialAsignacionSofkiano)
                .flatMap(repository::save)
                .map( historial -> Boolean.TRUE);
    }
}
