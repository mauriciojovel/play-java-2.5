package modules;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.cache.HandlerCache;
import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;
import security.ApplicationDeadboltHandler;
import security.CompositeConstraints;
import security.DeadboltHandlerCache;
import security.HandlerQualifiers;

import javax.inject.Singleton;

/**
 * Created by mjovel on 11-21-16.
 */
public class CustomDeadboltHook extends Module
{
    @Override
    public Seq<Binding<?>> bindings(final Environment environment,
                                    final Configuration configuration)
    {
        return seq(
          bind(DeadboltHandler.class).qualifiedWith(HandlerQualifiers.MainHandler.class)
                  .to(ApplicationDeadboltHandler.class).in(Singleton.class)
        , bind(HandlerCache.class).to(DeadboltHandlerCache.class).in(Singleton.class)
        , bind(CompositeConstraints.class).toSelf().eagerly());
    }
}
