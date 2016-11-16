package security;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.cache.HandlerCache;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjovel on 11-21-16.
 */
@Singleton
public class DeadboltHandlerCache implements HandlerCache {

    private final DeadboltHandler handler;
    private final Map<String, DeadboltHandler> handlers = new HashMap<>();

    @Inject
    public DeadboltHandlerCache(@HandlerQualifiers.MainHandler final DeadboltHandler handler)
    {
        this.handler = handler;
        handlers.put(handler.handlerName(), handler);
    }

    @Override
    public DeadboltHandler apply(final String key)
    {
        return handlers.get(key);
    }

    @Override
    public DeadboltHandler get()
    {
        return handler;
    }
}
