package security;

import models.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by mjovel on 11-21-16.
 */
public class Token implements Serializable {

    private User user;

    private Date expireDate;

    private Date createDate;

    public Token(User user) {
        this(user, null);
    }

    public Token(User user, Date expireDate) {
        this.user = user;
        this.expireDate = expireDate;
        this.createDate = new Date();
    }

    @Override
    public String toString() {
        String format = String.format("%s%d", user.email, createDate.getTime());
        String token = DigestUtils.sha256Hex(format);
        token += Objects.nonNull(expireDate) ? (" "+expireDate.getTime()) : "";
        return token;
    }
}
