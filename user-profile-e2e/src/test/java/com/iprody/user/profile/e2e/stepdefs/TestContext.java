package com.iprody.user.profile.e2e.stepdefs;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum class representing the Test Context, which is a thread-local storage for sharing data between steps in a
 * Cucumber test.
 * It provides methods for getting and setting data by name, as well as for getting and setting the request,
 * response, and payload (body) of a test.
 * It also includes a method for resetting the Test Context to its initial state.
 */
public enum TestContext {
    CONTEXT;

    /**
     * The name of the key used to store the request in the Test Context.
     */
    private static final String REQUEST_KEY = "REQUEST";
    /**
     * The name of the key used to store the response in the Test Context.
     */
    private static final String RESPONSE_KEY = "RESPONSE";
    /**
     * The name of the key used to store the payload (body) in the Test Context.
     */
    private static final String PAYLOAD_KEY = "PAYLOAD";
    /**
     * The thread-local storage for the Test Context.
     */
    private final ThreadLocal<Map<String, Object>> context = ThreadLocal.withInitial(HashMap::new);

    /**
     * Gets the value associated with the given name in the Test Context.
     *
     * @param <T>
     * @param name the name of the value to retrieve
     * @return the value associated with the given name, or null if no value is found
     */
    public <T> T get(String name) {
        return (T) context.get().get(name);
    }

    /**
     * Sets the value associated with the given name in the Test Context.
     *
     * @param <T>
     * @param name  the name of the value to set
     * @param value the value to associate with the given name
     * @return the previous value associated with the given name, or null if no previous value was found
     */
    public <T> T set(String name, T value) {
        return (T) context.get().put(name, value);
    }

    /**
     * Gets the payload (body) of the test as an object of the specified type.
     *
     * @param <T>
     * @param payloadType the type of the payload
     * @return the payload of the test as an object of the specified type
     */
    public <T> T getBody(Class<T> payloadType) {
        return payloadType.cast(get(PAYLOAD_KEY));
    }

    /**
     * Sets the payload (body) of the test to the given value.
     *
     * @param <T>
     * @param payload the payload to set
     */
    public <T> void setBody(T payload) {
        set(PAYLOAD_KEY, payload);
    }

    /**
     * Gets the response of the test as an object of the specified type.
     *
     * @param <T>
     * @param payloadType the type of the response
     * @return the response of the test as an object of the specified type
     */
    public <T> T getResponse(Class<T> payloadType) {
        return payloadType.cast(get(RESPONSE_KEY));
    }

    /**
     * Sets the response of the test to the given value.
     *
     * @param <T>
     * @param payload the response to set
     */
    public <T> void setResponse(T payload) {
        set(RESPONSE_KEY, payload);
    }

    /**
     * Gets the request of the test as an object of the specified type.
     *
     * @param <T>
     * @param payloadType the type of the request
     * @return the request of the test as an object of the specified type
     */
    public <T> T getRequest(Class<T> payloadType) {
        return payloadType.cast(get(REQUEST_KEY));
    }

    /**
     * Sets the request of the test to the given value.
     *
     * @param <T>
     * @param payload the request to set
     */
    public <T> void setRequest(T payload) {
        set(REQUEST_KEY, payload);
    }

    /**
     * Resets the Test Context to its initial state.
     */
    public void reset() {
        context.get().clear();
    }
}
