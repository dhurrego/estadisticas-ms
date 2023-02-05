package co.com.sofka.usecase.historialasignacionsofkiano;

import co.com.sofka.model.cliente.Cliente;
import co.com.sofka.model.commos.enums.TipoMovimiento;
import co.com.sofka.model.historialasignacionsofkiano.HistorialAsignacionSofkiano;
import co.com.sofka.model.historialasignacionsofkiano.dto.AgregarHistorialAsignacionSofkianoDTO;
import co.com.sofka.model.historialasignacionsofkiano.gateways.HistorialAsignacionSofkianoRepository;
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
class AgregarHistorialAsignacionSofkianoUseCaseTest {

    private static final String DNI = "CC123123";
    private static final String STRING_TEST = "TEST";
    private static final String NIT = "8908565441";
    private static final TipoMovimiento TIPO_MOVIMIENTO_INGRESO = TipoMovimiento.INGRESO;

    @InjectMocks
    private AgregarHistorialAsignacionSofkianoUseCase useCase;

    @Mock
    private HistorialAsignacionSofkianoRepository repository;

    private AgregarHistorialAsignacionSofkianoDTO agregarHistorialAsignacionSofkianoDTO;
    private Sofkiano sofkiano;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        sofkiano = Sofkiano.builder()
                .nombreCompleto(STRING_TEST)
                .dni(DNI)
                .build();

        cliente = Cliente.builder()
                .nit(NIT)
                .razonSocial(STRING_TEST)
                .build();

        agregarHistorialAsignacionSofkianoDTO = new AgregarHistorialAsignacionSofkianoDTO(sofkiano, cliente,
                TIPO_MOVIMIENTO_INGRESO);
    }

    @Test
    void agregarHistorialCambiosAsignacion() {
        HistorialAsignacionSofkiano historialAsignacionSofkiano = HistorialAsignacionSofkiano.builder()
                .id(UUID.randomUUID().toString())
                .sofkiano(sofkiano)
                .cliente(cliente)
                .tipoMovimiento(TIPO_MOVIMIENTO_INGRESO)
                .fechaMovimiento(LocalDateTime.now())
                .build();

        when(repository.save(any(HistorialAsignacionSofkiano.class)))
                .thenReturn(Mono.just(historialAsignacionSofkiano));

        StepVerifier.create(useCase.agregarHistorialCambiosAsignacion(agregarHistorialAsignacionSofkianoDTO))
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }
}