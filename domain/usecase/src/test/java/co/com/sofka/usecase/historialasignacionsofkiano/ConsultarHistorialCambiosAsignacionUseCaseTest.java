package co.com.sofka.usecase.historialasignacionsofkiano;

import co.com.sofka.model.cliente.Cliente;
import co.com.sofka.model.exception.negocio.BusinessException;
import co.com.sofka.model.historialasignacionsofkiano.CantidadCambiosAsignacion;
import co.com.sofka.model.historialasignacionsofkiano.gateways.HistorialAsignacionSofkianoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static co.com.sofka.model.exception.negocio.BusinessException.Tipo.ERROR_FECHA_FINAL_INFERIOR_INICIAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultarHistorialCambiosAsignacionUseCaseTest {

    private static final LocalDate FECHA_INICIAL = LocalDate.now();
    private static final LocalDate FECHA_FINAL = LocalDate.now();
    public static final String NIT = "899056052";
    public static final String STRING_TEST = "TEST";

    @InjectMocks
    private ConsultarHistorialCambiosAsignacionUseCase useCase;

    @Mock
    private HistorialAsignacionSofkianoRepository repository;

    private CantidadCambiosAsignacion cantidadCambiosAsignacion;

    @BeforeEach
    void setUp() {
        Cliente cliente = Cliente.builder()
                .razonSocial(STRING_TEST)
                .nit(NIT)
                .build();

        cantidadCambiosAsignacion = new CantidadCambiosAsignacion(cliente, 0, 0);
    }

    @Test
    void consultarCantidadCambiosAsignacionExitosamente() {
        when(repository.consultarCantidadCambiosAsignacion(any(), any()))
                .thenReturn(Flux.just(cantidadCambiosAsignacion));

        StepVerifier.create(useCase.consultarCantidadCambiosAsignacion(FECHA_INICIAL, FECHA_FINAL))
                .assertNext( cantidadCambiosAsignacionDTO -> {
                    assertEquals(cantidadCambiosAsignacion.cantidadIngresos(), cantidadCambiosAsignacionDTO.cantidadIngresos());
                    assertEquals(cantidadCambiosAsignacion.cantidadSalidas(), cantidadCambiosAsignacionDTO.cantidadSalidas());
                    assertEquals(cantidadCambiosAsignacion.cliente().getNit(),
                            cantidadCambiosAsignacionDTO.cliente().getNit());
                    assertEquals(cantidadCambiosAsignacion.cliente().getRazonSocial(),
                            cantidadCambiosAsignacionDTO.cliente().getRazonSocial());
                })
                .verifyComplete();
    }

    @Test
    void consultarCantidadCambiosAsignacionFallidoFechaFinalMenorFechaInicial() {
        StepVerifier.create(useCase.consultarCantidadCambiosAsignacion(FECHA_INICIAL, FECHA_FINAL.minusDays(1)))
                .expectError(BusinessException.class)
                .verify();

        StepVerifier.create(useCase.consultarCantidadCambiosAsignacion(FECHA_INICIAL, FECHA_FINAL.minusDays(1)))
                .expectErrorMessage(ERROR_FECHA_FINAL_INFERIOR_INICIAL.getMessage())
                .verify();
    }
}