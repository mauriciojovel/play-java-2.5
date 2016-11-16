package security;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.java.models.Permission;
import be.objectify.deadbolt.java.models.Subject;
import bussiness.PermissionBO;
import bussiness.UserBO;
import models.User;
import org.apache.commons.lang3.StringUtils;
import play.cache.CacheApi;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import scala.Option;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by mjovel on 11-21-16.
 */
@HandlerQualifiers.MainHandler
public class ApplicationDeadboltHandler implements DeadboltHandler {

    private final CacheApi cacheApi;

    private final PermissionBO permissionBO;

    private final UserBO userBO;

    @Inject
    public ApplicationDeadboltHandler(CacheApi cacheApi, PermissionBO permissionBO, UserBO userBO) {
        this.cacheApi = cacheApi;
        this.permissionBO = permissionBO;
        this.userBO = userBO;
    }

    @Override
    public CompletionStage<Optional<Result>> beforeAuthCheck(Http.Context context) {
        return CompletableFuture.completedFuture(Optional.empty());
    }

    @Override
    public CompletionStage<Optional<? extends Subject>> getSubject(Http.Context context) {
        String tokenString = context.request().getHeader(commons.Http.TOKEN_HEADER_REQUEST);
        User userOption = null;

        if(StringUtils.isNoneBlank(tokenString)) {
            userOption = cacheApi.get(tokenString);
        }

        return CompletableFuture.completedFuture(
                Optional.ofNullable(userOption)
        );
    }

    @Override
    public CompletionStage<Result> onAuthFailure(Http.Context context, Optional<String> content) {
        return CompletableFuture.completedFuture(Results.forbidden(content.isPresent()?content.get():""));
    }

    @Override
    public CompletionStage<Optional<DynamicResourceHandler>> getDynamicResourceHandler(Http.Context context) {
        return CompletableFuture.completedFuture(Optional.of(new ApplicationDynamicResourceHandler()));
    }

    @Override
    public CompletionStage<List<? extends Permission>> getPermissionsForRole(String roleName) {
        return  CompletableFuture.completedFuture(
                permissionBO.findByRole(roleName)
        );
    }
}
