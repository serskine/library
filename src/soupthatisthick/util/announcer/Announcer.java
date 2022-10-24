package soupthatisthick.util.announcer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * This class is a clever way to announce method calls to a list of objects. It eliminated the need for reqriting boiler plate code frequently.
 * @author user
 *
 * @param <T>
 */
public class Announcer<T extends EventListener> {
    private final T proxy;
    private final List<T> listeners = new ArrayList<T>();




    /**
     * Creates an announcer that will announce method calls on all of it's listeners.
     * @param listenerType
     */
    public Announcer(Class<? extends T> listenerType) {
        proxy = listenerType.cast(Proxy.newProxyInstance(
                listenerType.getClassLoader(),
                new Class<?>[]{listenerType},
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        announce(method, args);
                        return null;
                    }
                }));
    }

    /**
     * Returns the number of listeners in the announcer.
     * @return
     */
    public int size() {
        return listeners.size();
    }

    /**
     * Adds a listener to the announcer. The announcer will call methods on the given object.
     * @param listener
     */
    public void addListener(T listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from the announcer. The announcer will no longer call methods on that object
     * @param listener
     */
    public void removeListener(T listener) {
        listeners.remove(listener);
    }

    /**
     * Use this to represent calling the method on all listeners with the same argument
     * @return
     */
    public T announce() {
        return proxy;
    }

    /**
     * Private method called that invoked the specified method on all listeners
     * @param m
     * @param args
     */
    private void announce(Method m, Object[] args) {
        try {
            for (T listener : listeners) {
                m.invoke(listener, args);
            }
        }
        catch (IllegalAccessException e) {
            throw new IllegalArgumentException("could not invoke listener", e);
        }
        catch (InvocationTargetException e) {
            Throwable cause = e.getCause();

            if (cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            else if (cause instanceof Error) {
                throw (Error)cause;
            }
            else {
                throw new UnsupportedOperationException("listener threw exception", cause);
            }
        }
    }

    /**
     * Creates a new announcer that can announce listeners of the specified interface
     * @param listenerType
     * @return
     */
    public static <T extends EventListener> Announcer<T> to(Class<? extends T> listenerType) {
        return new Announcer<T>(listenerType);
    }
}