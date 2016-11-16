package controllers;

import bussiness.Crud;
import com.fasterxml.jackson.databind.JsonNode;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import static play.libs.Json.toJson;

/**
 * Created by mjovel on 11-15-16.
 */
@Transactional
public abstract  class CrudController<T, K extends Serializable> extends Controller {

    @Inject
    protected FormFactory formFactory;


    protected Class<T> entityClass;

    public CrudController() {
        entityClass = (Class<T>)
                ((ParameterizedType)getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {
        JsonNode json = request().body().asJson();
        Form<T> personForm = formFactory.form(entityClass).bind(json);
        if(personForm.hasErrors()) {
            return badRequest(personForm.errorsAsJson());
        } else {
            T person = personForm.get();
            getBussinessObject().persist(person);
            return ok(Json.toJson(person));
        }
    }

    @Transactional(readOnly = true)
    public Result all() {
        return ok(toJson(getBussinessObject().findAll()));
    }

    public abstract Crud<T, K> getBussinessObject();
}
