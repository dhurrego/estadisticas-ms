package co.com.sofka.sb.listener;

import co.com.sofka.model.commos.enums.TipoMovimiento;
import co.com.sofka.model.historialcambioestadosofkiano.dto.AgregarHistorialCambioEstadoDTO;
import co.com.sofka.model.sofkiano.Sofkiano;
import co.com.sofka.usecase.historialcambioestadosofkiano.AgregarHistorialCambiosEstadoUseCase;
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
class AgregarHistorialCambioEstadoMessagesListenerTest {

    private static final String DNI = "CC123123";
    private static final String STRING_TEST = "TEST";

    @InjectMocks
    private AgregarHistorialCambioEstadoMessagesListener listener;

    @Mock
    private AgregarHistorialCambiosEstadoUseCase useCase;

    private AgregarHistorialCambioEstadoDTO agregarHistorialCambioEstadoDTO;

    @BeforeEach
    void setUp() {
        Sofkiano sofkiano = Sofkiano.builder()
                .nombreCompleto(STRING_TEST)
                .dni(DNI)
                .build();

        agregarHistorialCambioEstadoDTO = new AgregarHistorialCambioEstadoDTO(sofkiano, TipoMovimiento.SALIDA);
    }

    @Test
    void procesarMensajeExitosamente() {
        when(useCase.agregarHistorialCambiosEstado(any())).thenReturn(Mono.just(Boolean.TRUE));

        listener.procesarMensaje(agregarHistorialCambioEstadoDTO);

        verify(useCase, times(1)).agregarHistorialCambiosEstado(any());
    }

    @Test
    void procesarMensajeConError() {
        when(useCase.agregarHistorialCambiosEstado(any())).thenReturn(Mono.error(new RuntimeException("Error")));

        listener.procesarMensaje(agregarHistorialCambioEstadoDTO);

        verify(useCase, times(1)).agregarHistorialCambiosEstado(any());
    }
}