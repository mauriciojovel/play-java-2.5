package constraints;



import play.data.validation.ValidationError;

import java.util.List;

/**
 * Created by mjovel on 11-15-16.
 */
public interface Validate<T> {
    void validate(T object, List<ValidationError> errors);
}
