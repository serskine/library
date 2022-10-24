package soupthatisthick.util.topics;

import java.util.EventListener;

public interface TopicListener extends EventListener {
    void onEvent(final Class<?> topic, final Object event);
}
