package com.iprody.user.profile.e2e.stepdefs;

import com.iprody.user.profile.e2e.generated.model.MappingResponse;
import com.iprody.user.profile.e2e.generated.model.WebHandler;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * The EndpointFinder class provides a static method to check if a given HTTP endpoint and method are available
 * in a given ResponseMappings object.
 */
@UtilityClass
public class EndpointFinder {

    /**
     * Checks if a given HTTP endpoint and method are available in the ResponseMappings object.
     *
     * @param responseMappings The ResponseMappings object to search in.
     * @param method           The HTTP method to search for (e.g. "GET", "POST", "PUT", etc.).
     * @param endpoint         The HTTP endpoint to search for (e.g. "/users", "/products", etc.).
     * @return true if an HTTP handler is found for the given method and endpoint; false otherwise.
     */
    public boolean endpointIsAvailable(MappingResponse responseMappings, String method, String endpoint) {
        final List<WebHandler> webHandlers = responseMappings
                .getContexts()
                .getApplication()
                .getMappings()
                .getDispatcherHandlers()
                .getWebHandler();
        for (WebHandler handler : webHandlers) {
            if (handler.getPredicate().startsWith(String.format("{%s %s", method, endpoint))) {
                return true;
            }
        }
        return false;
    }
}