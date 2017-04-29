package com.ubb.mpp.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Marius Adam
 */
public class MotoContestServer {
    private static final Logger logger = Logger.getLogger(MotoContestServer.class.getName());

    private Server server;

    private void start() throws IOException {
    /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new MotoContestImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                MotoContestServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final MotoContestServer server = new MotoContestServer();
        server.start();
        server.blockUntilShutdown();
    }

    static class MotoContestImpl extends MotoContestGrpc.MotoContestImplBase {

        private EventDispatcher eventDispatcher = new EventDispatcher();

        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            eventDispatcher.dispatch(
                    Event.newBuilder()
                            .setName(Event.Name.HELLO)
                            .build()
            );
        }

        @Override
        public void sayHelloAgain(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello again " + req.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            eventDispatcher.dispatch(
                    Event.newBuilder()
                            .setName(Event.Name.HELLO_AGAIN)
                            .build()
            );
        }

        @Override
        public void subscribe(SubscribeRequest request, StreamObserver<Event> responseObserver) {
            Set<Event.Name> requestSet = new HashSet<>(request.getEventList());
            requestSet.forEach(
                    name -> eventDispatcher.addListener(name, responseObserver)
            );
        }
    }
}
