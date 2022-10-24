package soupthatisthick.util.topics;

import soupthatisthick.util.announcer.Announcer;
import soupthatisthick.util.logger.Logger;

import java.util.HashMap;
import java.util.Map;

public class Topics {
    private static final Map<Class<?>, Announcer<TopicListener>> subscribers = new HashMap<>();

    /**
     * This will have a listener subscribe to a topic.
     * @param topic identifies the topic we want to listen to
     * @param subscriber is the object that wants to receive announced events of the topic type
     * @param <T> defines the type
     */
    public static <T> void subscribe(final Class<T> topic, final TopicListener subscriber) {
        try {
            final Announcer<TopicListener> listeners = subscribers.getOrDefault(topic, Announcer.to(TopicListener.class));
            listeners.addListener(subscriber);
            subscribers.put(topic, listeners);
        } catch (Exception e) {
            Logger.warning(e.getMessage(), e);
        }
    }

    /**
     * This will have a listener subscribe to a topic
     * @param topic is the class of the object we expect to receive and identifies the topic
     * @param subscriber is the object that no longer wants to subscribe
     * @param <T> defines the type of the topic
     */
    public static <T> void unsubscribe(final Class<T> topic, final TopicListener subscriber) {
        try {
            final Announcer<TopicListener> listeners = subscribers.get(topic);
            if (listeners != null) {
                listeners.removeListener(subscriber);
                if (listeners.size() == 0) {
                    subscribers.remove(topic);
                }
            }
        } catch (Exception e) {
            Logger.warning(e.getMessage(), e);
        }
    }

    /**
     * This will publish the listener
     * @param topic is the class of the event and identifies the topic
     * @param event is the event that was announced
     */
    public static void publish(final Class<?> topic, final Object event) {
        try {
            final Announcer<TopicListener> listeners = subscribers.get(topic);
            if (listeners != null) {
                listeners.announce().onEvent(topic, event);
            }
        } catch (Exception e) {
            Logger.warning(e.getMessage(), e);
        }
    }

    public static <T> void publish(final T event) {
        publish(event.getClass(), event);
    }

}
