package co.com.sofka.mongo.historialasignacionsofkiano;

import co.com.sofka.mongo.historialasignacionsofkiano.data.CantidadCambiosAsignacionData;
import co.com.sofka.mongo.historialasignacionsofkiano.data.HistorialAsignacionSofkianoData;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface HistorialAsignacionSofkianoDataRepository
        extends ReactiveMongoRepository<HistorialAsignacionSofkianoData, String>,
        ReactiveQueryByExampleExecutor<HistorialAsignacionSofkianoData> {

    @Aggregation(pipeline = {"""
                { $match:
                    {
                        fechaMovimiento: { $gte: ?0, $lt: ?1 }
                    }
                }""",
                """
                {
                    $group:{
                      _id: "$cliente",
                      cantidadIngresos: {
                          $sum:{
                              $cond: [ { $eq: [ "$tipoMovimiento", "INGRESO"] }, 1, 0]
                          }
                      },
                      cantidadSalidas : {
                          $sum:{
                              $cond: [ { $eq: [ "$tipoMovimiento", "SALIDA"] }, 1, 0]
                          }
                      }
                    }
                }"""})
    Flux<CantidadCambiosAsignacionData> consultarCantidadCambiosAsignacion(LocalDate fechaInicial, LocalDate fechaFinal);
}
