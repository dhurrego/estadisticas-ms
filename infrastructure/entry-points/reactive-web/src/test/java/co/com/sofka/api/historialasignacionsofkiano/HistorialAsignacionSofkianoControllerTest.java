package co.com.sofka.api.historialasignacionsofkiano;

import co.com.sofka.api.config.WebSecurityConfig;
import co.com.sofka.api.handler.ReactiveExceptionHandler;
import co.com.sofka.model.cliente.Cliente;
import co.com.sofka.model.historialasignacionsofkiano.dto.CantidadCambiosAsignacionDTO;
import co.com.sofka.usecase.historialasignacionsofkiano.ConsultarHistorialCambiosAsignacionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static co.com.sofka.model.exception.negocio.BusinessException.Tipo.ERROR_FECHA_FINAL_INFERIOR_INICIAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {HistorialAsignacionSofkianoController.class, ReactiveExceptionHandler.class})
@Import(WebSecurityConfig.class)
@AutoConfigureWebTestClient
@EnableAutoConfiguration
@ExtendWith(MockitoExtension.class)
class HistorialAsignacionSofkianoControllerTest {

    private static final String FECHA = "2023-02-03";

    @MockBean
    private ConsultarHistorialCambiosAsignacionUseCase consultarHistorialCambiosAsignacionUseCase;

    @Autowired
    private WebTestClient webTestClient;

    private CantidadCambiosAsignacionDTO cantidadCambiosAsignacionDTO;

    @BeforeEach
    void setUp() {
        Cliente cliente = Cliente.builder()
                .nit("890565231")
                .razonSocial("TEST")
                .build();

        cantidadCambiosAsignacionDTO = new CantidadCambiosAsignacionDTO(cliente, 1, 1);
    }

    @Test
    void consultarCambiosAsignacionExitosamente() {
        when(consultarHistorialCambiosAsignacionUseCase.consultarCantidadCambiosAsignacion(any(), any()))
                .thenReturn(Flux.just(cantidadCambiosAsignacionDTO));

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/cambios-asignaciones")
                                .queryParam("fechaInicial", FECHA)
                                .queryParam("fechaFinal", FECHA)
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON_VALUE)
                .expectBodyList(CantidadCambiosAsignacionDTO.class);
    }

    @Test
    void consultarCambiosAsignacionFallidoErrorDeNegocio() {
        when(consultarHistorialCambiosAsignacionUseCase.consultarCantidadCambiosAsignacion(any(), any()))
                .thenThrow(ERROR_FECHA_FINAL_INFERIOR_INICIAL.build());

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/cambios-asignaciones")
                                .queryParam("fechaInicial", FECHA)
                                .queryParam("fechaFinal", FECHA)
                                .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ProblemDetail.class);
    }
}