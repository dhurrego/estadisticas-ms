package co.com.sofka.sb.listener;

import co.com.sofka.model.cliente.Cliente;
import co.com.sofka.model.commos.enums.TipoMovimiento;
import co.com.sofka.model.historialasignacionsofkiano.dto.AgregarHistorialAsignacionSofkianoDTO;
import co.com.sofka.model.sofkiano.Sofkiano;
import co.com.sofka.usecase.historialasignacionsofkiano.AgregarHistorialAsignacionSofkianoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgregarAsignacionSofkianoMessagesListenerTest {

    private static final String DNI = "CC123123";
    private static final String STRING_TEST = "TEST";

    @InjectMocks
    private AgregarAsignacionSofkianoMessagesListener listener;

    @Mock
    private AgregarHistorialAsignacionSofkianoUseCase useCase;

    private AgregarHistorialAsignacionSofkianoDTO agregarHistorialAsignacionSofkianoDTO;
    
    @BeforeEach
    void setUp() {
        Sofkiano sofkiano = Sofkiano.builder()
                .nombreCompleto(STRING_TEST)
                .dni(DNI)
                .build();

        Cliente cliente = Cliente.builder()
                .nit("8908565441")
                .razonSocial(STRING_TEST)
                .build();

        agregarHistorialAsignacionSofkianoDTO = new AgregarHistorialAsignacionSofkianoDTO(sofkiano, cliente,
                TipoMovimiento.INGRESO);
    }

    @Test
    void procesarMensajeExitosamente() {
        when(useCase.agregarHistorialCambiosAsignacion(any())).thenReturn(Mono.just(Boolean.TRUE));

        listener.procesarMensaje(agregarHistorialAsignacionSofkianoDTO);

        verify(useCase, times(1)).agregarHistorialCambiosAsignacion(any());
    }

    @Test
    void procesarMensajeConError() {
        when(useCase.agregarHistorialCambiosAsignacion(any())).thenReturn(Mono.error(new RuntimeException("Error")));

        listener.procesarMensaje(agregarHistorialAsignacionSofkianoDTO);

        verify(useCase, times(1)).agregarHistorialCambiosAsignacion(any());
    }
}