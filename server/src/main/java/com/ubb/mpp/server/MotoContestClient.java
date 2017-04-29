package com.ubb.mpp.server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marius Adam
 */
public class MotoContestClient  {
    private static final Logger logger = Logger.getLogger(MotoContestClient.class.getName());

    private final ManagedChannel channel;
    private final MotoContestGrpc.MotoContestBlockingStub blockingStub;
    private final MotoContestGrpc.MotoContestStub asyncStub;

    /**
     * Construct client connecting to MotoContest server at {@code host:port}.
     */
    public MotoContestClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext(true));
    }

    /**
     * Construct client for accessing RouteGuide server using the existing channel.
     */
    MotoContestClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = MotoContestGrpc.newBlockingStub(channel);
        asyncStub = MotoContestGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
    }

    /**
     * Say hello to server.
     */
    public void greet(String name) {
        logger.info("Will try to greet " + name + " ...");
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;
        try {
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response.getMessage());

        try {
            response = blockingStub.sayHelloAgain(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response.getMessage());
    }

    public CountDownLatch awaitEvents(List<Integer> events) {
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<Event> responseObserver = new StreamObserver<Event>() {
            @Override
            public void onNext(Event value) {
                logger.info("received event " + value.getName());
            }

            @Override
            public void onError(Throwable t) {
                logger.warning(t.getMessage());
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                logger.info("Finished receiving events");
                finishLatch.countDown();
            }
        };

        SubscribeRequest.Builder srb = SubscribeRequest.newBuilder();
        events.forEach(
                new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        srb.addEvent(Event.Name.forNumber(integer));
                    }
                }
        );
        SubscribeRequest sr = srb.build();

        asyncStub.subscribe(sr, responseObserver);
        if (events.isEmpty()) {
            finishLatch.countDown();
        }

        return finishLatch;
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        MotoContestClient client = new MotoContestClient("localhost", 50051);
        try {
      /* Access a service running on the local machine on port 50051 */
            String user = "world";
            if (args.length > 0) {
                user = args[0]; /* Use the arg as the name to greet if provided */
            }
            client.greet(user);



            CountDownLatch finishLatch = client.awaitEvents(getIntegerList(args));

            finishLatch.await();
        } finally {
            client.shutdown();
        }
    }

    public static List<Integer> getIntegerList(String[] args) {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < args.length; i++) {
            try {
                result.add(Integer.parseInt(args[i]));
            } catch (NumberFormatException e) {
                logger.warning("Argument number " + i + " is invalid");
            }
        }

        return result;
    }
}
