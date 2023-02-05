package co.com.sofka.model.historialcambioestadosofkiano.factoria;

import co.com.sofka.model.historialcambioestadosofkiano.HistorialCambiosEstadoSofkiano;
import co.com.sofka.model.historialcambioestadosofkiano.dto.AgregarHistorialCambioEstadoDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

import static co.com.sofka.model.exception.negocio.BusinessException.Tipo.ERROR_INFORMACION_SOFKIANO_REQUERIDO;
import static co.com.sofka.model.exception.negocio.BusinessException.Tipo.ERROR_TIPO_MOVIMIENTO_REQUERIDO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HistorialCambiosEstadoSofkianoFactory {

    public static HistorialCambiosEstadoSofkiano crearHistorialCambioEstado(
            AgregarHistorialCambioEstadoDTO agregarHistorialCambioEstadoDTO) {

        validarCamposObligatorios(agregarHistorialCambioEstadoDTO);

        return HistorialCambiosEstadoSofkiano.builder()
                .sofkiano(agregarHistorialCambioEstadoDTO.sofkiano())
                .tipoMovimiento(agregarHistorialCambioEstadoDTO.tipoMovimiento())
                .fechaMovimiento(LocalDateTime.now())
                .build();
    }

    private static void validarCamposObligatorios(AgregarHistorialCambioEstadoDTO agregarHistorialCambioEstadoDTO) {
        if(Objects.isNull(agregarHistorialCambioEstadoDTO.sofkiano())) {
            throw ERROR_INFORMACION_SOFKIANO_REQUERIDO.build();
        }

        if(Objects.isNull(agregarHistorialCambioEstadoDTO.tipoMovimiento())) {
            throw ERROR_TIPO_MOVIMIENTO_REQUERIDO.build();
        }
    }
}
