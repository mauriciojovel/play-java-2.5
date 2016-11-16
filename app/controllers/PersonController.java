package controllers;

import bussiness.Crud;
import bussiness.PersonBO;
import models.Person;
import play.data.Form;
import play.libs.Json;
import play.mvc.Result;

import javax.inject.Inject;


public class PersonController extends CrudController<Person, Long> {

    private final PersonBO personBO;

    @Inject
    public PersonController(PersonBO personBO) {
        this.personBO = personBO;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result addPerson() {
        Form<Person> personForm = formFactory.form(Person.class).bindFromRequest();
        if(personForm.hasErrors()) {
            return redirect(routes.PersonController.index());
        } else {
            Person person = personForm.get();
            personBO.persist(person);
            return redirect(routes.PersonController.index());
        }
    }

    public Result addPersonJson() {
        Form<Person> personForm = formFactory.form(Person.class).bindFromRequest();
        if(personForm.hasErrors()) {
            return badRequest(personForm.errorsAsJson());
        } else {
            Person person = personForm.get();
            personBO.persist(person);
            return ok(Json.toJson(person));
        }
    }

    @Override
    public Crud<Person, Long> getBussinessObject() {
        return personBO;
    }
}
