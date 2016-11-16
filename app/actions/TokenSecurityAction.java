package actions;

import org.apache.commons.lang3.StringUtils;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by mjovel on 11-22-16.
 */
public class TokenSecurityAction extends Action<TokenSecurity> {

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {
        if(!configuration.skip()) {
            String token = ctx.request().getHeader(commons.Http.TOKEN_HEADER_REQUEST);
            if(StringUtils.isBlank(token)) {
                return CompletableFuture.completedFuture(Results.forbidden("no token"));
            } else {
                int sepPos = token.indexOf(" ");
                if(sepPos >= 0) {
                    // Has expiration time
                    String timeString = token.substring(sepPos + 1);
                    Date date = new Date(Long.parseLong(timeString));
                    Date now = new Date();
                    if(date.before(now)) {
                        return CompletableFuture.completedFuture(Results.forbidden("token expired"));
                    }
                }
            }
        }
        return delegate.call(ctx);
    }
}
