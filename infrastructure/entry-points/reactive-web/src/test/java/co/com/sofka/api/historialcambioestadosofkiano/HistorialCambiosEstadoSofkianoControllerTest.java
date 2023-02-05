package co.com.sofka.api.historialcambioestadosofkiano;

import co.com.sofka.api.handler.ResponseExceptionHandler;
import co.com.sofka.model.historialcambioestadosofkiano.dto.CantidadCambiosEstadoDTO;
import co.com.sofka.usecase.historialcambioestadosofkiano.ConsultarHistorialCambiosEstadoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static co.com.sofka.model.exception.negocio.BusinessException.Tipo.ERROR_FECHA_FINAL_INFERIOR_INICIAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {HistorialCambiosEstadoSofkianoController.class, ResponseExceptionHandler.class})
@AutoConfigureWebTestClient
@EnableAutoConfiguration
@ExtendWith(MockitoExtension.class)
class HistorialCambiosEstadoSofkianoControllerTest {

    private static final String FECHA = "2023-02-03";

    @MockBean
    private ConsultarHistorialCambiosEstadoUseCase consultarHistorialCambiosEstadoUseCase;

    @Autowired
    private WebTestClient webTestClient;

    private CantidadCambiosEstadoDTO cantidadCambiosEstadoDTO;

    @BeforeEach
    void setUp() {
        cantidadCambiosEstadoDTO = new CantidadCambiosEstadoDTO(3, 0);
    }

    @Test
    void consultarCantidadCambiosEstadoExitoso() {
        when(consultarHistorialCambiosEstadoUseCase.consultarCantidadCambiosEstado(any(), any()))
                .thenReturn(Mono.just(cantidadCambiosEstadoDTO));

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/cambios-estados-sofkianos")
                                .queryParam("fechaInicial", FECHA)
                                .queryParam("fechaFinal", FECHA)
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON_VALUE)
                .expectBody(CantidadCambiosEstadoDTO.class);
    }

    @Test
    void consultarCantidadCambiosEstadoConErrorDeNegocio() {
        when(consultarHistorialCambiosEstadoUseCase.consultarCantidadCambiosEstado(any(), any()))
                .thenThrow(ERROR_FECHA_FINAL_INFERIOR_INICIAL.build());

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/cambios-estados-sofkianos")
                                .queryParam("fechaInicial", FECHA)
                                .queryParam("fechaFinal", FECHA)
                                .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ProblemDetail.class);
    }

    @Test
    void consultarCantidadCambiosEstadoConErrorFormatoFecha() {
        when(consultarHistorialCambiosEstadoUseCase.consultarCantidadCambiosEstado(any(), any()))
                .thenReturn(Mono.just(cantidadCambiosEstadoDTO));

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/cambios-estados-sofkianos")
                                .queryParam("fechaInicial", FECHA)
                                .queryParam("fechaFinal", "2023/02/03")
                                .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ProblemDetail.class);
    }

    @Test
    void consultarCantidadCambiosEstadoConErrorMetodoNoPermitido() {
        when(consultarHistorialCambiosEstadoUseCase.consultarCantidadCambiosEstado(any(), any()))
                .thenReturn(Mono.just(cantidadCambiosEstadoDTO));

        webTestClient.post().uri("/cambios-estados-sofkianos")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.METHOD_NOT_ALLOWED)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ProblemDetail.class);
    }

    @Test
    void consultarCantidadCambiosEstadoConErrorGeneral() {
        when(consultarHistorialCambiosEstadoUseCase.consultarCantidadCambiosEstado(any(), any()))
                .thenThrow(new RuntimeException("Error"));

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/cambios-estados-sofkianos")
                                .queryParam("fechaInicial", FECHA)
                                .queryParam("fechaFinal", FECHA)
                                .build())
                .exchange()
                .expectStatus().is5xxServerError()
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ProblemDetail.class);
    }
}