package security;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import play.mvc.Http;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by mjovel on 11-21-16.
 */
public class ApplicationDynamicResourceHandler implements DynamicResourceHandler {
    @Override
    public CompletionStage<Boolean> isAllowed(String name, Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
        return CompletableFuture.completedFuture(Boolean.FALSE);
    }

    @Override
    public CompletionStage<Boolean> checkPermission(String permissionValue, Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
        return CompletableFuture.completedFuture(Boolean.FALSE);
    }
}
