package controllers;

import actions.TokenSecurity;

import java.io.Serializable;

/**
 * Created by mjovel on 11-22-16.
 */
@TokenSecurity
public abstract class TokenSecurityController<T, K extends Serializable> extends RestrictedCrudController<T, K> {
}
