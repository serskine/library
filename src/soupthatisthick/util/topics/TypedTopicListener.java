package soupthatisthick.util.topics;

public interface TypedTopicListener<T> extends TopicListener {
    default void onEvent(final Class<?> topic, final Object event) {
        onEvent((T) event);
    }

    void onEvent(T event);
}
