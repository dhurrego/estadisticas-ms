package co.com.sofka.mongo.helper;

import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

import static co.com.sofka.model.exception.tecnico.TechnicalException.Tipo.ERROR_COMUNICACION_BASE_DATOS;

@Slf4j
public abstract class AdapterOperations<E, D, I, R extends ReactiveCrudRepository<D, I> & ReactiveQueryByExampleExecutor<D>> {

    protected R repository;
    protected ObjectMapper mapper;
    private final Class<D> dataClass;
    private final Function<D, E> toEntityFn;

    @SuppressWarnings("unchecked")
    protected AdapterOperations(R repository, ObjectMapper mapper, Function<D, E> toEntityFn) {
        this.repository = repository;
        this.mapper = mapper;
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.dataClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
        this.toEntityFn = toEntityFn;
    }

    public Mono<E> save(E entity) {
        return Mono.just(entity)
                .map(this::toData)
                .flatMap(this::saveData)
                .map(this::toEntity);
    }

    public Mono<E> findById(I id) {
        return doQuery(repository.findById(id))
                .onErrorResume(throwable -> {
                    log.error(throwable.toString());
                    return Mono.error(ERROR_COMUNICACION_BASE_DATOS::build);
                });
    }

    public Flux<E> findAll() {
        return doQueryMany(repository.findAll())
                .onErrorResume(throwable -> {
                    log.error(throwable.toString());
                    return Mono.error(ERROR_COMUNICACION_BASE_DATOS::build);
                });
    }

    protected Mono<E> doQuery(Mono<D> query) {
        return query.map(this::toEntity);
    }

    protected Flux<E> doQueryMany(Flux<D> query) {
        return query.map(this::toEntity);
    }

    protected Mono<D> saveData(D data) {
        return repository.save(data)
                .onErrorResume(throwable -> {
                    log.error(throwable.toString());
                    return Mono.error(ERROR_COMUNICACION_BASE_DATOS::build);
                });
    }

    protected D toData(E entity) {
        return mapper.map(entity, dataClass);
    }

    protected E toEntity(D data) {
        return toEntityFn.apply(data);
    }

}
