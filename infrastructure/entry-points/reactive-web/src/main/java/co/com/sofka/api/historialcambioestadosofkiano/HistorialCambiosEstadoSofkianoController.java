package co.com.sofka.api.historialcambioestadosofkiano;

import co.com.sofka.model.historialcambioestadosofkiano.dto.CantidadCambiosEstadoDTO;
import co.com.sofka.usecase.historialcambioestadosofkiano.ConsultarHistorialCambiosEstadoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
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

    @Operation(summary = "Servicio para visualizar los cambios de estado durante una fecha determinada. ", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CantidadCambiosEstadoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))) })
    @GetMapping()
    public Mono<ResponseEntity<CantidadCambiosEstadoDTO>> consultarCantidadCambiosEstado(
            @RequestParam LocalDate fechaInicial, @RequestParam LocalDate fechaFinal) {

        return consultarHistorialCambiosEstadoUseCase.consultarCantidadCambiosEstado(fechaInicial, fechaFinal)
                .map(ResponseEntity::ok);
    }
}
