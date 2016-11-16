package controllers;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import commons.Http;
import actions.TokenSecurity;
import java.io.Serializable;

/**
 * Created by mjovel on 11-21-16.
 */
@SubjectPresent
public abstract class RestrictedCrudController<T, K extends Serializable>
        extends CrudController<T, K> {

    public String getToken() {
        return request().getHeader(Http.TOKEN_HEADER_REQUEST);
    }
}
