package controllers;

import actions.TokenSecurity;
import be.objectify.deadbolt.java.actions.Composite;
import bussiness.Crud;
import models.User;
import play.mvc.BodyParser;
import play.mvc.Result;
import security.CompositeNames;

/**
 * Created by mjovel on 11-22-16.
 */
public class ExampleController extends TokenSecurityController<User, Long> {
    @Override
    public Crud<User, Long> getBussinessObject() {
        return null;
    }

    @BodyParser.Of(BodyParser.Empty.class)
    @TokenSecurity
    public Result restricted() {
        return ok();
    }

    @BodyParser.Of(BodyParser.Empty.class)
    @TokenSecurity
    @Composite(CompositeNames.CREATE)
    public Result create() {
        return ok();
    }

    @BodyParser.Of(BodyParser.Empty.class)
    @TokenSecurity
    @Composite(CompositeNames.ADMIN_ACCESS)
    public Result adminAccess() {
        return ok();
    }
}
