package bussiness;


import models.Role;
import models.Role_;
import models.User;
import models.User_;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import play.cache.CacheApi;
import security.Token;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.criteria.*;
import java.util.Optional;

/**
 * Created by mjovel on 11-21-16.
 */
@Singleton
public class UserBO extends BasicBO<User, Long> {

    private static final Logger Log = play.Logger.underlying();

    private final CacheApi cacheApi;

    @Inject
    public UserBO(CacheApi cacheApi) {
        this.cacheApi = cacheApi;
    }

    public Optional<Token> login(String email, String password) {
        CriteriaBuilder cq = em().getCriteriaBuilder();
        CriteriaQuery<User> query = cq.createQuery(User.class);
        Root<User> from = query.from(User.class);
        String passwordEnc = DigestUtils.sha256Hex(password);
        Fetch<User, Role> fetchRole = from.fetch(User_.roles, JoinType.LEFT);
        fetchRole.fetch(Role_.permissions, JoinType.LEFT);
        from.fetch(User_.person, JoinType.LEFT);
        from.fetch(User_.customPermissions, JoinType.LEFT);
        query.where(
                cq.equal(from.get(User_.email), email)
                ,cq.equal(from.get(User_.password), passwordEnc)
        );
        try {
            Token token;
            User user = em().createQuery(query).getSingleResult();
            token = new Token(user);
            user.token = token.toString();
            em().persist(user);
            cacheApi.set(token.toString(), user);
            return Optional.of(token);
        } catch (Exception e) {
            Log.debug("We can't find the user: {}", email, e);
            return Optional.empty();
        }
    }
}
