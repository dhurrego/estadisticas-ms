package co.com.sofka.mongo.historialcambioestadosofkiano;

import co.com.sofka.mongo.historialcambioestadosofkiano.data.CantidadCambiosEstadoSofkianoData;
import co.com.sofka.mongo.historialcambioestadosofkiano.data.HistorialCambiosEstadoSofkianoData;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface HistorialCambiosEstadoSofkianoDataRepository
        extends ReactiveMongoRepository<HistorialCambiosEstadoSofkianoData, String>,
        ReactiveQueryByExampleExecutor<HistorialCambiosEstadoSofkianoData> {

    @Aggregation(pipeline = {"""
                { $match:
                    {
                        fechaMovimiento: { $gte: ?0, $lt: ?1 }
                    }
                }""",
                """
                { $group:
                    {
                        _id: "$tipoMovimiento",
                        cantidadMovimientos: {
                            $sum: 1
                        }
                    }
                }"""})
    Flux<CantidadCambiosEstadoSofkianoData> consultarCantidadCambiosEstado(LocalDate fechaInicial, LocalDate fechaFinal);
}
