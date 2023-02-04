package co.com.sofka.sb.listener;

import co.com.sofka.model.historialcambioestadosofkiano.dto.AgregarHistorialCambioEstadoDTO;
import co.com.sofka.usecase.historialcambioestadosofkiano.AgregarHistorialCambiosEstadoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgregarHistorialCambioEstadoMessagesListener {

    private static final String TOPIC_NAME = "cambioestadosofkiano";
    private static final String SUBSCRIPTION_NAME = "estadisticas-cambioestado-ms";

    private final AgregarHistorialCambiosEstadoUseCase useCase;

    @JmsListener(destination = TOPIC_NAME, containerFactory = "topicJmsListenerContainerFactory",
            subscription = SUBSCRIPTION_NAME)
    public void procesarMensaje(AgregarHistorialCambioEstadoDTO agregarHistorialCambioEstadoDTO) {
        log.info("Mensaje recibido: {}", agregarHistorialCambioEstadoDTO);
        useCase.agregarHistorialCambiosEstado(agregarHistorialCambioEstadoDTO)
                .doOnSuccess(unused -> log.info("Se proceso el mensaje exitosamente: {}", agregarHistorialCambioEstadoDTO))
                .onErrorResume(throwable -> {
                    log.error("El mensaje se proceso con error: {}", throwable.getMessage());
                    return Mono.empty();
                })
                .block();
    }

}
