package co.com.sofka.model.historialasignacionsofkiano.factoria;

import co.com.sofka.model.cliente.Cliente;
import co.com.sofka.model.commos.enums.TipoMovimiento;
import co.com.sofka.model.exception.negocio.BusinessException;
import co.com.sofka.model.historialasignacionsofkiano.HistorialAsignacionSofkiano;
import co.com.sofka.model.historialasignacionsofkiano.dto.AgregarHistorialAsignacionSofkianoDTO;
import co.com.sofka.model.sofkiano.Sofkiano;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static co.com.sofka.model.exception.negocio.BusinessException.Tipo.*;
import static org.junit.jupiter.api.Assertions.*;

class HistorialAsignacionSofkianoFactoryTest {

    private static final String DNI = "CC123123";
    private static final String STRING_TEST = "TEST";
    private static final String NIT = "8908565441";
    private static final TipoMovimiento TIPO_MOVIMIENTO_INGRESO = TipoMovimiento.INGRESO;

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
    void crearHistorialAsignacionSofkianoExitosamente() {
        HistorialAsignacionSofkiano historialAsignacionSofkiano = HistorialAsignacionSofkianoFactory
                .crearHistorialAsignacionSofkiano(agregarHistorialAsignacionSofkianoDTO);

        assertNull(historialAsignacionSofkiano.getId());
        assertEquals(DNI, historialAsignacionSofkiano.getSofkiano().getDni());
        assertEquals(STRING_TEST, historialAsignacionSofkiano.getSofkiano().getNombreCompleto());
        assertEquals(NIT, historialAsignacionSofkiano.getCliente().getNit());
        assertEquals(TIPO_MOVIMIENTO_INGRESO, historialAsignacionSofkiano.getTipoMovimiento());
    }

    @Test
    void crearHistorialAsignacionSofkianoFallidoSofkianoNull() {
        agregarHistorialAsignacionSofkianoDTO = new AgregarHistorialAsignacionSofkianoDTO(null, cliente,
                TIPO_MOVIMIENTO_INGRESO);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> HistorialAsignacionSofkianoFactory
                        .crearHistorialAsignacionSofkiano(agregarHistorialAsignacionSofkianoDTO));

        assertEquals(ERROR_INFORMACION_SOFKIANO_REQUERIDO.getMessage(), exception.getMessage());
    }

    @Test
    void crearHistorialAsignacionSofkianoFallidoClienteNull() {
        agregarHistorialAsignacionSofkianoDTO = new AgregarHistorialAsignacionSofkianoDTO(sofkiano, null,
                TIPO_MOVIMIENTO_INGRESO);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> HistorialAsignacionSofkianoFactory
                        .crearHistorialAsignacionSofkiano(agregarHistorialAsignacionSofkianoDTO));

        assertEquals(ERROR_INFORMACION_CLIENTE_REQUERIDO.getMessage(), exception.getMessage());
    }

    @Test
    void crearHistorialAsignacionSofkianoFallidoTipoMovimientoNull() {
        agregarHistorialAsignacionSofkianoDTO = new AgregarHistorialAsignacionSofkianoDTO(sofkiano, cliente,
                null);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> HistorialAsignacionSofkianoFactory
                        .crearHistorialAsignacionSofkiano(agregarHistorialAsignacionSofkianoDTO));

        assertEquals(ERROR_TIPO_MOVIMIENTO_REQUERIDO.getMessage(), exception.getMessage());
    }
}