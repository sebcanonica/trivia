package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.function.Consumer;

import static org.mockito.Mockito.times;

public class EventPublisherTest {

    static class TestConsumer implements Consumer<String> {
        @Override
        public void accept(String s) {
            System.out.println(s);
        }
    }

    @Test
    public void applyEvents() {
        TestConsumer consumerMock1 = Mockito.mock(TestConsumer.class);
        TestConsumer consumerMock2 = Mockito.mock(TestConsumer.class);
        EventPublisher publisher = new EventPublisher();
        publisher.registerHandler(String.class, consumerMock1);
        publisher.registerHandler(String.class, consumerMock2);

        String event_to_consume = "event to consume";
        publisher.applyEvents(Collections.singletonList(event_to_consume));

        Mockito.verify(consumerMock1, times(1)).accept(event_to_consume);
        Mockito.verify(consumerMock2, times(1)).accept(event_to_consume);
    }
}