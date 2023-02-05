package co.com.sofka.model.exception.negocio;

import co.com.sofka.model.exception.BaseException;
import lombok.Getter;

public class BusinessException extends BaseException {
    public enum Tipo {
        ERROR_FECHA_FINAL_INFERIOR_INICIAL("La fecha final debe ser superior o igual a la fecha inicial", 400),
        ERROR_INFORMACION_SOFKIANO_REQUERIDO("La información del sofkiano es obligatoria", 400),
        ERROR_INFORMACION_CLIENTE_REQUERIDO("La información del cliente es obligatoria", 400),
        ERROR_TIPO_MOVIMIENTO_REQUERIDO("El tipo de movimiento es obligatorio", 400);

        @Getter
        private final String message;

        @Getter
        private final int httpStatusCode;

        Tipo(String message, int httpStatusCode) {
            this.message = message;
            this.httpStatusCode = httpStatusCode;
        }

        public BusinessException build() {
            return new BusinessException(this);
        }
    }
    public BusinessException(Tipo tipo) {
        super(tipo.getMessage(), tipo.getHttpStatusCode());
    }
}