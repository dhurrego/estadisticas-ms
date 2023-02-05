package co.com.sofka.api.historialasignacionsofkiano;

import co.com.sofka.model.historialasignacionsofkiano.dto.CantidadCambiosAsignacionDTO;
import co.com.sofka.usecase.historialasignacionsofkiano.ConsultarHistorialCambiosAsignacionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
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

    @Operation(summary = "Servicio para visualizar los cambios de asignación durante una fecha determinada. ", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CantidadCambiosAsignacionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))) })
    @GetMapping()
    public Mono<ResponseEntity<Flux<CantidadCambiosAsignacionDTO>>> consultarCambiosAsignacion(
            @RequestParam LocalDate fechaInicial, @RequestParam LocalDate fechaFinal) {

        return Mono.just(ResponseEntity.ok()
                .body(consultarHistorialCambiosAsignacionUseCase
                        .consultarCantidadCambiosAsignacion(fechaInicial, fechaFinal)));
    }
}
