package com.ubb.mpp.server.events;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Marius Adam
 */
@Component
public class RmqEventDispatcher implements EventDispatcher {
    private static final Logger logger = Logger.getLogger(RmqEventDispatcher.class.getName());
    private static final String EXCHANGE_NAME = "motocontest_events";
    private Connection connection;
    private Channel channel;

    @Autowired
    public RmqEventDispatcher(Connection connection, Channel channel) throws IOException {
        this.connection = connection;
        this.channel = channel;

        // Declare a topic exchange so that the client decides
        // to what events what to subscribe
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
    }

    @Override
    public void dispatch(Event event) {
        try {
            logger.info("Dispatching " + event.toString());
            channel.basicPublish(EXCHANGE_NAME, event.toString(), null, new byte[0]);
        } catch (IOException e) {
            logger.warning(e.getMessage());

            // nothing much that can do here
            // bubble up the exception
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (connection != null) {
            connection.close();
        }
    }
}
