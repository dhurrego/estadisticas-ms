package co.com.sofka.mongo.historialasignacionsofkiano;

import co.com.sofka.model.cliente.Cliente;
import co.com.sofka.model.commos.enums.TipoMovimiento;
import co.com.sofka.model.exception.tecnico.TechnicalException;
import co.com.sofka.model.historialasignacionsofkiano.HistorialAsignacionSofkiano;
import co.com.sofka.model.sofkiano.Sofkiano;
import co.com.sofka.mongo.cliente.ClienteData;
import co.com.sofka.mongo.historialasignacionsofkiano.data.CantidadCambiosAsignacionData;
import co.com.sofka.mongo.historialasignacionsofkiano.data.HistorialAsignacionSofkianoData;
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

import static co.com.sofka.model.exception.tecnico.TechnicalException.Tipo.ERROR_COMUNICACION_BASE_DATOS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistorialAsignacionSofkianoRepositoryAdapterTest {

    private static final LocalDate FECHA_INICIAL = LocalDate.now();
    private static final LocalDate FECHA_FINAL = LocalDate.now();
    private static final String DNI = "CC123123";
    private static final String NIT = "895060522";
    private static final String STRING_TEST = "TEST";
    private static final TipoMovimiento TIPO_MOVIMIENTO = TipoMovimiento.INGRESO;
    private static final LocalDateTime FECHA_MOVIMIENTO = LocalDateTime.now();
    public static final String ID_HISTORIAL = "123";

    @InjectMocks
    private HistorialAsignacionSofkianoRepositoryAdapter adapter;

    @Mock
    private HistorialAsignacionSofkianoDataRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    private CantidadCambiosAsignacionData cantidadCambiosEstadoSofkianoData;
    private HistorialAsignacionSofkianoData historialAsignacionSofkianoData;
    private HistorialAsignacionSofkiano historialAsignacionSofkiano;

    @BeforeEach
    void setUp() {
        SofkianoData sofkianoData = SofkianoData.builder()
                .dni(DNI)
                .nombreCompleto(STRING_TEST)
                .build();

        ClienteData clienteData = ClienteData.builder()
                .nit(NIT)
                .razonSocial(STRING_TEST)
                .build();

        cantidadCambiosEstadoSofkianoData = CantidadCambiosAsignacionData.builder()
                .cliente(clienteData)
                .cantidadIngresos(0)
                .cantidadSalidas(0)
                .build();

        historialAsignacionSofkianoData = HistorialAsignacionSofkianoData.builder()
                .id(ID_HISTORIAL)
                .sofkiano(sofkianoData)
                .cliente(clienteData)
                .tipoMovimiento(TIPO_MOVIMIENTO)
                .fechaMovimiento(FECHA_MOVIMIENTO)
                .build();

        Sofkiano sofkiano = Sofkiano.builder()
                .dni(DNI)
                .nombreCompleto(STRING_TEST)
                .build();

        Cliente cliente = Cliente.builder()
                .nit(NIT)
                .razonSocial(STRING_TEST)
                .build();

        historialAsignacionSofkiano = HistorialAsignacionSofkiano.builder()
                .id(ID_HISTORIAL)
                .sofkiano(sofkiano)
                .cliente(cliente)
                .tipoMovimiento(TIPO_MOVIMIENTO)
                .fechaMovimiento(FECHA_MOVIMIENTO)
                .build();
    }

    @Test
    void consultarCantidadCambiosAsignacionExitoso() {
        when(repository.consultarCantidadCambiosAsignacion(any(), any()))
                .thenReturn(Flux.just(cantidadCambiosEstadoSofkianoData));

        StepVerifier.create(adapter.consultarCantidadCambiosAsignacion(FECHA_INICIAL, FECHA_FINAL))
                .assertNext(cantidadCambiosAsignacion -> {
                    assertEquals(0, cantidadCambiosAsignacion.cantidadIngresos());
                    assertEquals(0, cantidadCambiosAsignacion.cantidadSalidas());
                    assertNotNull(cantidadCambiosAsignacion.cliente());
                    assertEquals(NIT, cantidadCambiosAsignacion.cliente().getNit());
                    assertEquals(STRING_TEST, cantidadCambiosAsignacion.cliente().getRazonSocial());
                })
                .verifyComplete();
    }

    @Test
    void consultarCantidadCambiosAsignacionFallidoDesconexionBD() {
        when(repository.consultarCantidadCambiosAsignacion(any(), any()))
                .thenReturn(Flux.error(new RuntimeException("Desconexion base de datos")));

        StepVerifier.create(adapter.consultarCantidadCambiosAsignacion(FECHA_INICIAL, FECHA_FINAL))
                .expectError(TechnicalException.class)
                .verify();

        StepVerifier.create(adapter.consultarCantidadCambiosAsignacion(FECHA_INICIAL, FECHA_FINAL))
                .expectErrorMessage(ERROR_COMUNICACION_BASE_DATOS.getMessage())
                .verify();
    }

    @Test
    void listarPorIdExitoso() {
        when(objectMapper.mapBuilder(any(HistorialAsignacionSofkianoData.class),
                eq(HistorialAsignacionSofkiano.HistorialAsignacionSofkianoBuilder.class))
        ).thenReturn(historialAsignacionSofkiano.toBuilder());

        when(repository.findById(anyString()))
                .thenReturn(Mono.just(historialAsignacionSofkianoData));

        StepVerifier.create(adapter.findById(ID_HISTORIAL))
                .assertNext(historialAsignacionSofkiano -> {
                    assertEquals(ID_HISTORIAL, historialAsignacionSofkiano.getId());
                    assertEquals(TIPO_MOVIMIENTO, historialAsignacionSofkiano.getTipoMovimiento());
                    assertEquals(DNI, historialAsignacionSofkiano.getSofkiano().getDni());
                    assertEquals(STRING_TEST, historialAsignacionSofkiano.getSofkiano().getNombreCompleto());
                    assertEquals(NIT, historialAsignacionSofkiano.getCliente().getNit());
                    assertEquals(STRING_TEST, historialAsignacionSofkiano.getCliente().getRazonSocial());
                    assertEquals(FECHA_MOVIMIENTO, historialAsignacionSofkiano.getFechaMovimiento());
                })
                .verifyComplete();
    }

    @Test
    void listarPorIdFallidoDesconexionBD() {
        when(repository.findById(anyString()))
                .thenReturn(Mono.error(new RuntimeException("Desconexion base de datos")));

        StepVerifier.create(adapter.findById("132"))
                .expectError(TechnicalException.class)
                .verify();

        StepVerifier.create(adapter.findById("132"))
                .expectErrorMessage(ERROR_COMUNICACION_BASE_DATOS.getMessage())
                .verify();
    }

    @Test
    void listarTodoExitoso() {
        when(objectMapper.mapBuilder(any(HistorialAsignacionSofkianoData.class),
                eq(HistorialAsignacionSofkiano.HistorialAsignacionSofkianoBuilder.class))
        ).thenReturn(historialAsignacionSofkiano.toBuilder());

        when(repository.findAll())
                .thenReturn(Flux.just(historialAsignacionSofkianoData));

        StepVerifier.create(adapter.findAll())
                .assertNext(historialAsignacionSofkiano -> {
                    assertEquals(ID_HISTORIAL, historialAsignacionSofkiano.getId());
                    assertEquals(TIPO_MOVIMIENTO, historialAsignacionSofkiano.getTipoMovimiento());
                    assertEquals(DNI, historialAsignacionSofkiano.getSofkiano().getDni());
                    assertEquals(STRING_TEST, historialAsignacionSofkiano.getSofkiano().getNombreCompleto());
                    assertEquals(NIT, historialAsignacionSofkiano.getCliente().getNit());
                    assertEquals(STRING_TEST, historialAsignacionSofkiano.getCliente().getRazonSocial());
                    assertEquals(FECHA_MOVIMIENTO, historialAsignacionSofkiano.getFechaMovimiento());
                })
                .verifyComplete();
    }

    @Test
    void listarTodoFallidoDesconexionBD() {
        when(repository.findAll())
                .thenReturn(Flux.error(new RuntimeException("Desconexion base de datos")));

        StepVerifier.create(adapter.findAll())
                .expectError(TechnicalException.class)
                .verify();

        StepVerifier.create(adapter.findAll())
                .expectErrorMessage(ERROR_COMUNICACION_BASE_DATOS.getMessage())
                .verify();
    }

    @Test
    void guardarExitoso() {
        when(objectMapper.mapBuilder(any(HistorialAsignacionSofkianoData.class),
                eq(HistorialAsignacionSofkiano.HistorialAsignacionSofkianoBuilder.class))
        ).thenReturn(historialAsignacionSofkiano.toBuilder());

        when(objectMapper.map(any(HistorialAsignacionSofkiano.class),
                eq(HistorialAsignacionSofkianoData.class))
        ).thenReturn(historialAsignacionSofkianoData);

        when(repository.save(any()))
                .thenReturn(Mono.just(historialAsignacionSofkianoData));

        StepVerifier.create(adapter.save(historialAsignacionSofkiano))
                .assertNext(historial -> {
                    assertEquals(ID_HISTORIAL, historial.getId());
                    assertEquals(TIPO_MOVIMIENTO, historial.getTipoMovimiento());
                    assertEquals(DNI, historial.getSofkiano().getDni());
                    assertEquals(STRING_TEST, historial.getSofkiano().getNombreCompleto());
                    assertEquals(NIT, historial.getCliente().getNit());
                    assertEquals(STRING_TEST, historial.getCliente().getRazonSocial());
                    assertEquals(FECHA_MOVIMIENTO, historial.getFechaMovimiento());
                })
                .verifyComplete();
    }

    @Test
    void guardarDesconexionBD() {
        when(objectMapper.map(any(HistorialAsignacionSofkiano.class),
                eq(HistorialAsignacionSofkianoData.class))
        ).thenReturn(historialAsignacionSofkianoData);

        when(repository.save(any())).thenReturn(Mono.error(new RuntimeException("Desconexion base de datos")));

        StepVerifier.create(adapter.save(historialAsignacionSofkiano))
                .expectError(TechnicalException.class)
                .verify();

        StepVerifier.create(adapter.save(historialAsignacionSofkiano))
                .expectErrorMessage(ERROR_COMUNICACION_BASE_DATOS.getMessage())
                .verify();
    }
}