package co.com.sofka.usecase.historialcambioestadosofkiano;

import co.com.sofka.model.commos.enums.TipoMovimiento;
import co.com.sofka.model.historialcambioestadosofkiano.HistorialCambiosEstadoSofkiano;
import co.com.sofka.model.historialcambioestadosofkiano.dto.AgregarHistorialCambioEstadoDTO;
import co.com.sofka.model.historialcambioestadosofkiano.gateways.HistorialCambiosEstadoSofkianoRepository;
import co.com.sofka.model.sofkiano.Sofkiano;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgregarHistorialCambiosEstadoUseCaseTest {

    private static final String DNI = "CC123123";
    private static final String STRING_TEST = "TEST";
    private static final TipoMovimiento TIPO_MOVIMIENTO_INGRESO = TipoMovimiento.INGRESO;

    @InjectMocks
    private AgregarHistorialCambiosEstadoUseCase useCase;

    @Mock
    private HistorialCambiosEstadoSofkianoRepository repository;

    private AgregarHistorialCambioEstadoDTO agregarHistorialCambioEstadoDTO;
    private Sofkiano sofkiano;

    @BeforeEach
    void setUp() {
        sofkiano = Sofkiano.builder()
                .nombreCompleto(STRING_TEST)
                .dni(DNI)
                .build();

        agregarHistorialCambioEstadoDTO = new AgregarHistorialCambioEstadoDTO(sofkiano, TIPO_MOVIMIENTO_INGRESO);
    }

    @Test
    void agregarHistorialCambiosEstado() {
        HistorialCambiosEstadoSofkiano historialCambiosEstadoSofkiano = HistorialCambiosEstadoSofkiano.builder()
                .id(UUID.randomUUID().toString())
                .sofkiano(sofkiano)
                .tipoMovimiento(TIPO_MOVIMIENTO_INGRESO)
                .fechaMovimiento(LocalDateTime.now())
                .build();

        when(repository.save(any(HistorialCambiosEstadoSofkiano.class)))
                .thenReturn(Mono.just(historialCambiosEstadoSofkiano));

        StepVerifier.create(useCase.agregarHistorialCambiosEstado(agregarHistorialCambioEstadoDTO))
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }
}