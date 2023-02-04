package co.com.sofka.usecase.historialcambioestadosofkiano;

import co.com.sofka.model.exception.negocio.BusinessException;
import co.com.sofka.model.historialcambioestadosofkiano.CantidadCambiosEstadoSofkiano;
import co.com.sofka.model.historialcambioestadosofkiano.gateways.HistorialCambiosEstadoSofkianoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static co.com.sofka.model.exception.negocio.BusinessException.Tipo.ERROR_FECHA_FINAL_INFERIOR_INICIAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultarHistorialCambiosEstadoUseCaseTest {

    private static final LocalDate FECHA_INICIAL = LocalDate.now();
    private static final LocalDate FECHA_FINAL = LocalDate.now();

    @InjectMocks
    private ConsultarHistorialCambiosEstadoUseCase useCase;

    @Mock
    private HistorialCambiosEstadoSofkianoRepository repository;

    private CantidadCambiosEstadoSofkiano cambiosEstadoSofkiano;

    @BeforeEach
    void setUp() {
        cambiosEstadoSofkiano = new CantidadCambiosEstadoSofkiano(0, 0);
    }

    @Test
    void consultarCantidadCambiosEstado() {
        when(repository.consultarCantidadCambiosEstados(any(), any()))
                .thenReturn(Mono.just(cambiosEstadoSofkiano));

        StepVerifier.create(useCase.consultarCantidadCambiosEstado(FECHA_INICIAL, FECHA_FINAL))
                .assertNext( cantidadCambiosEstadoDTO -> {
                    assertEquals(cambiosEstadoSofkiano.cantidadIngresos(), cantidadCambiosEstadoDTO.cantidadIngresos());
                    assertEquals(cambiosEstadoSofkiano.cantidadSalidas(), cantidadCambiosEstadoDTO.cantidadSalidas());
                })
                .verifyComplete();
    }

    @Test
    void consultarCantidadCambiosEstadoFallidoFechaFinalMenorFechaInicial() {
        StepVerifier.create(useCase.consultarCantidadCambiosEstado(FECHA_INICIAL, FECHA_FINAL.minusDays(1)))
                .expectError(BusinessException.class)
                .verify();

        StepVerifier.create(useCase.consultarCantidadCambiosEstado(FECHA_INICIAL, FECHA_FINAL.minusDays(1)))
                .expectErrorMessage(ERROR_FECHA_FINAL_INFERIOR_INICIAL.getMessage())
                .verify();
    }
}