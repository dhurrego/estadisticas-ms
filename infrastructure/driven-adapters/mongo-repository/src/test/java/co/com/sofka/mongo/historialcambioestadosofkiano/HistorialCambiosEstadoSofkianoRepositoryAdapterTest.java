package co.com.sofka.mongo.historialcambioestadosofkiano;

import co.com.sofka.model.commos.enums.TipoMovimiento;
import co.com.sofka.model.exception.tecnico.TechnicalException;
import co.com.sofka.model.historialcambioestadosofkiano.HistorialCambiosEstadoSofkiano;
import co.com.sofka.model.sofkiano.Sofkiano;
import co.com.sofka.mongo.historialcambioestadosofkiano.data.CantidadCambiosEstadoSofkianoData;
import co.com.sofka.mongo.historialcambioestadosofkiano.data.HistorialCambiosEstadoSofkianoData;
import co.com.sofka.mongo.sofkiano.SofkianoData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static co.com.sofka.model.exception.tecnico.TechnicalException.Tipo.ERROR_COMUNICACION_BASE_DATOS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistorialCambiosEstadoSofkianoRepositoryAdapterTest {

    private static final String DNI = "CC1231233";
    private static final String STRING_TEST = "TEST";
    private static final TipoMovimiento TIPO_MOVIMIENTO = TipoMovimiento.INGRESO;
    private static final LocalDateTime FECHA_MOVIMIENTO = LocalDateTime.now();
    public static final String ID_HISTORIAL = "123";

    private static final LocalDate FECHA_INICIAL = LocalDate.now();
    private static final LocalDate FECHA_FINAL = LocalDate.now();

    @InjectMocks
    private HistorialCambiosEstadoSofkianoRepositoryAdapter adapter;

    @Mock
    private HistorialCambiosEstadoSofkianoDataRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    private List<CantidadCambiosEstadoSofkianoData> cantidadCambiosEstadoSofkianoData;
    private HistorialCambiosEstadoSofkiano historialCambiosEstadoSofkiano;
    private HistorialCambiosEstadoSofkianoData historialCambiosEstadoSofkianoData;

    @BeforeEach
    void setUp() {
        CantidadCambiosEstadoSofkianoData cantidadCambiosEstadoSofkianoDataIngreso = CantidadCambiosEstadoSofkianoData
                .builder()
                .cantidadMovimientos(0)
                .tipoMovimiento(TipoMovimiento.INGRESO)
                .build();

        CantidadCambiosEstadoSofkianoData cantidadCambiosEstadoSofkianoDataSalida = CantidadCambiosEstadoSofkianoData
                .builder()
                .cantidadMovimientos(0)
                .tipoMovimiento(TipoMovimiento.SALIDA)
                .build();

        cantidadCambiosEstadoSofkianoData = List.of(cantidadCambiosEstadoSofkianoDataIngreso,
                cantidadCambiosEstadoSofkianoDataSalida);

        SofkianoData sofkianoData = SofkianoData.builder()
                .dni(DNI)
                .nombreCompleto(STRING_TEST)
                .build();

        Sofkiano sofkiano = Sofkiano.builder()
                .dni(DNI)
                .nombreCompleto(STRING_TEST)
                .build();

        historialCambiosEstadoSofkiano = HistorialCambiosEstadoSofkiano.builder()
                .id(ID_HISTORIAL)
                .sofkiano(sofkiano)
                .tipoMovimiento(TIPO_MOVIMIENTO)
                .fechaMovimiento(FECHA_MOVIMIENTO)
                .build();

        historialCambiosEstadoSofkianoData = HistorialCambiosEstadoSofkianoData.builder()
                .id(ID_HISTORIAL)
                .sofkiano(sofkianoData)
                .tipoMovimiento(TIPO_MOVIMIENTO)
                .fechaMovimiento(FECHA_MOVIMIENTO)
                .build();
    }

    @Test
    void consultarCantidadCambiosEstadosExitoso() {
        when(repository.consultarCantidadCambiosEstado(any(), any()))
                .thenReturn(Flux.fromIterable(cantidadCambiosEstadoSofkianoData));

        StepVerifier.create(adapter.consultarCantidadCambiosEstados(FECHA_INICIAL, FECHA_FINAL))
                .assertNext(cantidadCambiosEstadoSofkiano -> {
                    assertEquals(0, cantidadCambiosEstadoSofkiano.cantidadIngresos());
                    assertEquals(0, cantidadCambiosEstadoSofkiano.cantidadSalidas());
                })
                .verifyComplete();
    }

    @Test
    void consultarCantidadCambiosEstadosFallidoErrorBaseDatos() {
        when(repository.consultarCantidadCambiosEstado(any(), any()))
                .thenReturn(Flux.error(new RuntimeException("Desconexion base de datos")));

        StepVerifier.create(adapter.consultarCantidadCambiosEstados(FECHA_INICIAL, FECHA_FINAL))
                .expectError(TechnicalException.class)
                .verify();

        StepVerifier.create(adapter.consultarCantidadCambiosEstados(FECHA_INICIAL, FECHA_FINAL))
                .expectErrorMessage(ERROR_COMUNICACION_BASE_DATOS.getMessage())
                .verify();
    }

    @Test
    void listarPorIdExitoso() {
        when(objectMapper.mapBuilder(any(HistorialCambiosEstadoSofkianoData.class),
                eq(HistorialCambiosEstadoSofkiano.HistorialCambiosEstadoSofkianoBuilder.class))
        ).thenReturn(historialCambiosEstadoSofkiano.toBuilder());

        when(repository.findById(anyString()))
                .thenReturn(Mono.just(historialCambiosEstadoSofkianoData));

        StepVerifier.create(adapter.findById(ID_HISTORIAL))
                .assertNext(historialAsignacionSofkiano -> {
                    assertEquals(ID_HISTORIAL, historialAsignacionSofkiano.getId());
                    assertEquals(TIPO_MOVIMIENTO, historialAsignacionSofkiano.getTipoMovimiento());
                    assertEquals(DNI, historialAsignacionSofkiano.getSofkiano().getDni());
                    assertEquals(STRING_TEST, historialAsignacionSofkiano.getSofkiano().getNombreCompleto());
                    assertEquals(FECHA_MOVIMIENTO, historialAsignacionSofkiano.getFechaMovimiento());
                })
                .verifyComplete();
    }
}