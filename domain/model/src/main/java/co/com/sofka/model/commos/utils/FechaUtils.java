package co.com.sofka.model.commos.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static co.com.sofka.model.exception.negocio.BusinessException.Tipo.ERROR_FECHA_FINAL_INFERIOR_INICIAL;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FechaUtils {

    public static void validarFechaFinal(LocalDate fechaInicial, LocalDate fechaFinal) {
        if(fechaFinal.isBefore(fechaInicial)) {
            throw ERROR_FECHA_FINAL_INFERIOR_INICIAL.build();
        }
    }
}
