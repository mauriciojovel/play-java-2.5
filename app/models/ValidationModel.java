package models;

import constraints.Validate;
import play.api.Play;
import play.data.validation.ValidationError;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjovel on 11-15-16.
 */
public class ValidationModel <M, T extends Validate<M>> {
    private Class<T> bussinesObject;
    private M objValidate;

    public ValidationModel() {
        bussinesObject = (Class<T>)
                ((ParameterizedType)getClass().getGenericSuperclass())
                        .getActualTypeArguments()[1];
        objValidate = (M) this;
    }

    public List<ValidationError> validate() {
        Validate<M> validate = Play.current().injector().instanceOf(bussinesObject);
        ArrayList<ValidationError> errors = new ArrayList<>();
        validate.validate(objValidate, errors);
        return errors.isEmpty() ? null : errors;
    }
}
