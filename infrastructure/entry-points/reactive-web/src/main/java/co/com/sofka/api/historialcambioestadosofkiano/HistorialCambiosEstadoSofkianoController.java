package co.com.sofka.api.historialcambioestadosofkiano;

import co.com.sofka.model.historialcambioestadosofkiano.dto.CantidadCambiosEstadoDTO;
import co.com.sofka.usecase.historialcambioestadosofkiano.ConsultarHistorialCambiosEstadoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/cambios-estados-sofkianos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class HistorialCambiosEstadoSofkianoController {

    private final ConsultarHistorialCambiosEstadoUseCase consultarHistorialCambiosEstadoUseCase;

    @GetMapping()
    public Mono<ResponseEntity<CantidadCambiosEstadoDTO>> consultarCantidadCambiosEstado(
            @RequestParam LocalDate fechaInicial, @RequestParam LocalDate fechaFinal) {

        return consultarHistorialCambiosEstadoUseCase.consultarCantidadCambiosEstado(fechaInicial, fechaFinal)
                .map(ResponseEntity::ok);
    }
}
