package controllers;

import be.objectify.deadbolt.java.actions.SubjectNotPresent;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import bussiness.UserBO;
import constraints.ValidationGroups;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import security.Token;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by mjovel on 11-21-16.
 */
@BodyParser.Of(BodyParser.Json.class)
@SubjectPresent
@Transactional
public class AuthenticatorController extends Controller {

    private final UserBO userBO;


    private final FormFactory formFactory;

    @Inject
    public AuthenticatorController(UserBO userBO, FormFactory formFactory) {
        this.userBO = userBO;
        this.formFactory = formFactory;
    }

    @SubjectNotPresent
    public Result login() {
        Form<User> userForm = formFactory.form(User.class, ValidationGroups.UserLogin.class)
                .bind(request().body().asJson());
        User user;
        Optional<Token> optional;
        if(userForm.hasErrors()) {
            return badRequest(userForm.errorsAsJson());
        }
        user = userForm.get();
        optional = userBO.login(user.email, user.password);
        if(optional.isPresent()) {
            return ok(optional.get().toString());
        } else {
            return badRequest();
        }
    }


}
