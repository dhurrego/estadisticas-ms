package co.com.sofka.sb.listener;

import co.com.sofka.model.historialasignacionsofkiano.dto.AgregarHistorialAsignacionSofkianoDTO;
import co.com.sofka.usecase.historialasignacionsofkiano.AgregarHistorialAsignacionSofkianoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgregarAsignacionSofkianoMessagesListener {

    private static final String TOPIC_NAME = "actualizacionasignacionsofkiano";
    private static final String SUBSCRIPTION_NAME = "estadisticas-asignacion-ms";

    private final AgregarHistorialAsignacionSofkianoUseCase useCase;

    @JmsListener(destination = TOPIC_NAME, containerFactory = "topicJmsListenerContainerFactory",
            subscription = SUBSCRIPTION_NAME)
    public void procesarMensaje(AgregarHistorialAsignacionSofkianoDTO agregarHistorialAsignacionSofkianoDTO) {
        log.info("Mensaje recibido: {}", agregarHistorialAsignacionSofkianoDTO);
        useCase.agregarHistorialCambiosAsignacion(agregarHistorialAsignacionSofkianoDTO)
                .doOnSuccess(unused -> log.info("Se proceso el mensaje exitosamente: {}", agregarHistorialAsignacionSofkianoDTO))
                .onErrorResume( throwable -> {
                    log.error("El mensaje se proceso con error: {}", throwable.getMessage());
                    return Mono.empty();
                })
                .block();
    }

}
