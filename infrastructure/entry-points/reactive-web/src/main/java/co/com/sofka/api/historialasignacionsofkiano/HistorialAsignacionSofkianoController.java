package co.com.sofka.api.historialasignacionsofkiano;

import co.com.sofka.model.historialasignacionsofkiano.dto.CantidadCambiosAsignacionDTO;
import co.com.sofka.usecase.historialasignacionsofkiano.ConsultarHistorialCambiosAsignacionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/cambios-asignaciones")
@RequiredArgsConstructor
public class HistorialAsignacionSofkianoController {

    private final ConsultarHistorialCambiosAsignacionUseCase consultarHistorialCambiosAsignacionUseCase;

    @GetMapping()
    public Mono<ResponseEntity<Flux<CantidadCambiosAsignacionDTO>>> consultarCambiosAsignacion(
            @RequestParam LocalDate fechaInicial, @RequestParam LocalDate fechaFinal) {

        return Mono.just(ResponseEntity.ok()
                .body(consultarHistorialCambiosAsignacionUseCase
                        .consultarCantidadCambiosAsignacion(fechaInicial, fechaFinal)));
    }
}
