package com.example;

import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import static com.example.Constants.TCP_PORT;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private final Disposable server;
    private final DataPublisher dataPublisher = new DataPublisher();
    private final GameController gameController;

    public Server() {
        this.gameController = new GameController("Server Player");
        RSocketImpl rSocket = new RSocketImpl(dataPublisher, gameController);
        this.server = RSocketFactory.receive()
                .acceptor((setupPayload, reactiveSocket) -> Mono.just(rSocket))
                .transport(TcpServerTransport.create("localhost", TCP_PORT))
                .start()
                .doOnNext(x -> LOG.info("Server started"))
                .subscribe();
    }

    public void dispose() {
        dataPublisher.complete();
        this.server.dispose();
    }

}
