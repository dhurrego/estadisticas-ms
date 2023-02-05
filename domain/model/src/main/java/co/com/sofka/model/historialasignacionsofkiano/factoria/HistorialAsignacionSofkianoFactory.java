package co.com.sofka.model.historialasignacionsofkiano.factoria;

import co.com.sofka.model.historialasignacionsofkiano.HistorialAsignacionSofkiano;
import co.com.sofka.model.historialasignacionsofkiano.dto.AgregarHistorialAsignacionSofkianoDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

import static co.com.sofka.model.exception.negocio.BusinessException.Tipo.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HistorialAsignacionSofkianoFactory {

    public static HistorialAsignacionSofkiano crearHistorialAsignacionSofkiano(
            AgregarHistorialAsignacionSofkianoDTO agregarHistorialAsignacionSofkianoDTO) {

        validarCamposObligatorios(agregarHistorialAsignacionSofkianoDTO);

        return HistorialAsignacionSofkiano.builder()
                .sofkiano(agregarHistorialAsignacionSofkianoDTO.sofkiano())
                .cliente(agregarHistorialAsignacionSofkianoDTO.cliente())
                .tipoMovimiento(agregarHistorialAsignacionSofkianoDTO.tipoMovimiento())
                .fechaMovimiento(LocalDateTime.now())
                .build();
    }

    private static void validarCamposObligatorios(AgregarHistorialAsignacionSofkianoDTO agregarHistorialAsignacionSofkianoDTO) {
        if(Objects.isNull(agregarHistorialAsignacionSofkianoDTO.sofkiano())) {
            throw ERROR_INFORMACION_SOFKIANO_REQUERIDO.build();
        }

        if(Objects.isNull(agregarHistorialAsignacionSofkianoDTO.cliente())) {
            throw ERROR_INFORMACION_CLIENTE_REQUERIDO.build();
        }

        if(Objects.isNull(agregarHistorialAsignacionSofkianoDTO.tipoMovimiento())) {
            throw ERROR_TIPO_MOVIMIENTO_REQUERIDO.build();
        }
    }
}
