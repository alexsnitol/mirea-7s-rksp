package com.example;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.Constants.DATA_STREAM_NAME;

public class RSocketImpl extends AbstractRSocket {

    private final DataPublisher dataPublisher;
    private final GameController gameController;


    public RSocketImpl(
            DataPublisher dataPublisher,
            GameController gameController
    ) {
        this.dataPublisher = dataPublisher;
        this.gameController = gameController;
    }


    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        try {
            return Mono.just(payload);
        } catch (Exception x) {
            return Mono.error(x);
        }
    }

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        try {
            dataPublisher.publish(payload);
            return Mono.empty();
        } catch (Exception x) {
            return Mono.error(x);
        }
    }

    @Override
    public Flux<Payload> requestStream(Payload payload) {
        String streamName = payload.getDataUtf8();
        if (DATA_STREAM_NAME.equals(streamName)) {
            return Flux.from(dataPublisher);
        }
        return Flux.error(new IllegalArgumentException(streamName));
    }

    @Override
    public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
        Flux.from(payloads)
                .subscribe(gameController::processPayload);
        Flux<Payload> channel = Flux.from(gameController);
        return channel;
    }

}
