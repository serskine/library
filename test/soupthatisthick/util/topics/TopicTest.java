package soupthatisthick.util.topics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soupthatisthick.util.Util;
import soupthatisthick.util.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TopicTest implements TopicListener {

    List<Object> events;

    public class MessageType1 {
        public final String message;

        public MessageType1(final String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " => " + message;
        }
    }

    public class MessageType2 {
        public final String message;

        public MessageType2(final String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " => " + message;
        }
    }

    public class RecordingTopicListener<T> implements TypedTopicListener<T> {
        public final List<T> messages = new ArrayList<>();

        @Override
        public void onEvent(T event) {
            this.messages.add(event);
        }
    }

    private RecordingTopicListener<MessageType1> listener1;
    private RecordingTopicListener<MessageType2> listener2;
    private List<Object> messages;

    @BeforeEach
    public void onSetup() {
        messages = new ArrayList<>();

        listener1 = new RecordingTopicListener<>();
        listener2 = new RecordingTopicListener<>();
        events = new ArrayList<>();

        Topics.subscribe(MessageType1.class, listener1);
        Topics.subscribe(MessageType2.class, listener2);

        Topics.subscribe(MessageType1.class, this);
        Topics.subscribe(MessageType2.class, this);

    }

    @AfterEach
    public void onTeardown() {

        for(Object message : messages) {
            Logger.info("THIS : " + message.toString());
        }

        for(Object message : listener1.messages) {
            Logger.info("listener1 : " + message.toString());
        }

        for(Object message : listener2.messages) {
            Logger.info("listener2 : " + message.toString());
        }

        Topics.unsubscribe(MessageType1.class, listener1);
        Topics.unsubscribe(MessageType2.class, listener2);
        Topics.unsubscribe(MessageType1.class, this);
        Topics.unsubscribe(MessageType2.class, this);
    }

    @Test
    public void CRUD() {

        final List<Object> sentMessages = new ArrayList<>();

        sentMessages.add(new MessageType1("M1 - 1"));
        sentMessages.add(new MessageType1("M1 - 2"));
        sentMessages.add(new MessageType1("M1 - 3"));
        sentMessages.add(new MessageType1("M1 - 4"));

        sentMessages.add(new MessageType2("M2 - 1"));
        sentMessages.add(new MessageType2("M2 - 2"));
        sentMessages.add(new MessageType2("M2 - 3"));
        sentMessages.add(new MessageType2("M2 - 4"));

        final List<Object> shuffledMessages = Util.randomShuffle(sentMessages);

        assertEquals(sentMessages.size(), shuffledMessages.size());

        for(final Object message : shuffledMessages) {
            Topics.publish(message.getClass(), message);
        }

        assertEquals(shuffledMessages.size(), messages.size());

        for(int i=0; i<shuffledMessages.size(); i++) {
            final Object expectedMessage = shuffledMessages.get(i);
            final Object observedMessage = messages.get(i);

            assertEquals(expectedMessage, observedMessage);
        }
    }

    @Override
    public void onEvent(Class<?> topic, Object event) {
        this.messages.add(event);
    }


}
