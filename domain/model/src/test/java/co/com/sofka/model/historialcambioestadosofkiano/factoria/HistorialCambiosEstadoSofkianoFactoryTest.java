package co.com.sofka.model.historialcambioestadosofkiano.factoria;

import co.com.sofka.model.commos.enums.TipoMovimiento;
import co.com.sofka.model.exception.negocio.BusinessException;
import co.com.sofka.model.historialcambioestadosofkiano.HistorialCambiosEstadoSofkiano;
import co.com.sofka.model.historialcambioestadosofkiano.dto.AgregarHistorialCambioEstadoDTO;
import co.com.sofka.model.sofkiano.Sofkiano;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static co.com.sofka.model.exception.negocio.BusinessException.Tipo.ERROR_INFORMACION_SOFKIANO_REQUERIDO;
import static co.com.sofka.model.exception.negocio.BusinessException.Tipo.ERROR_TIPO_MOVIMIENTO_REQUERIDO;
import static org.junit.jupiter.api.Assertions.*;

class HistorialCambiosEstadoSofkianoFactoryTest {

    private static final String DNI = "CC123123";
    private static final String STRING_TEST = "TEST";
    public static final TipoMovimiento TIPO_MOVIMIENTO_SALIDA = TipoMovimiento.SALIDA;

    private AgregarHistorialCambioEstadoDTO agregarHistorialCambioEstadoDTO;

    private Sofkiano sofkiano;

    @BeforeEach
    void setUp() {
        sofkiano = Sofkiano.builder()
                .nombreCompleto(STRING_TEST)
                .dni(DNI)
                .build();

        agregarHistorialCambioEstadoDTO = new AgregarHistorialCambioEstadoDTO(sofkiano, TIPO_MOVIMIENTO_SALIDA);
    }

    @Test
    void crearHistorialCambioEstadoExitosamente() {
        HistorialCambiosEstadoSofkiano historialCambiosEstadoSofkiano = HistorialCambiosEstadoSofkianoFactory
                .crearHistorialCambioEstado(agregarHistorialCambioEstadoDTO);

        assertNull(historialCambiosEstadoSofkiano.getId());
        assertEquals(DNI, historialCambiosEstadoSofkiano.getSofkiano().getDni());
        assertEquals(STRING_TEST, historialCambiosEstadoSofkiano.getSofkiano().getNombreCompleto());
        assertEquals(TIPO_MOVIMIENTO_SALIDA, historialCambiosEstadoSofkiano.getTipoMovimiento());
    }

    @Test
    void crearHistorialCambioEstadoFallidoSofkianoNulo() {
        agregarHistorialCambioEstadoDTO = new AgregarHistorialCambioEstadoDTO(null, TIPO_MOVIMIENTO_SALIDA);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> HistorialCambiosEstadoSofkianoFactory
                        .crearHistorialCambioEstado(agregarHistorialCambioEstadoDTO));

        assertEquals(ERROR_INFORMACION_SOFKIANO_REQUERIDO.getMessage(), exception.getMessage());
    }

    @Test
    void crearHistorialCambioEstadoFallidoTipoMovimientoNulo() {
        agregarHistorialCambioEstadoDTO = new AgregarHistorialCambioEstadoDTO(sofkiano, null);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> HistorialCambiosEstadoSofkianoFactory
                        .crearHistorialCambioEstado(agregarHistorialCambioEstadoDTO));

        assertEquals(ERROR_TIPO_MOVIMIENTO_REQUERIDO.getMessage(), exception.getMessage());
    }
}