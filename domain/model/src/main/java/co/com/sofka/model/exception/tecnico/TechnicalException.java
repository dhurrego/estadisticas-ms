package co.com.sofka.model.exception.tecnico;

import co.com.sofka.model.exception.BaseException;
import lombok.Getter;

public class TechnicalException extends BaseException {

    public enum Tipo {
        ERROR_COMUNICACION_BASE_DATOS("Error al intentar comunicarse con la base de datos", 500),
        ACCESO_NO_AUTORIZADO("Acceso no autorizado", 401),
        TOKEN_INVALIDO("Token invalido o expirado", 401),
        ACCESO_NO_PERMITIDO("Acceso no permitido", 403);

        @Getter
        private final String message;

        @Getter
        private final Integer httpStatusCode;

        Tipo(String message, Integer httpStatusCode) {
            this.message = message;
            this.httpStatusCode = httpStatusCode;
        }

        public TechnicalException build() {
            return new TechnicalException(this);
        }
    }

    public TechnicalException(Tipo tipo) {
        super(tipo.getMessage(),tipo.getHttpStatusCode());
    }
}
