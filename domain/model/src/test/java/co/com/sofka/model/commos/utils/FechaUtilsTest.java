package co.com.sofka.model.commos.utils;

import co.com.sofka.model.exception.negocio.BusinessException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static co.com.sofka.model.exception.negocio.BusinessException.Tipo.ERROR_FECHA_FINAL_INFERIOR_INICIAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FechaUtilsTest {
    @Test
    void validarFechaFinalError() {
        final LocalDate fechaInicial = LocalDate.now();
        final LocalDate fechaFinal = fechaInicial.minusDays(1);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> FechaUtils.validarFechaFinal(fechaInicial, fechaFinal));

        assertEquals(ERROR_FECHA_FINAL_INFERIOR_INICIAL.getMessage(), exception.getMessage());
    }
}