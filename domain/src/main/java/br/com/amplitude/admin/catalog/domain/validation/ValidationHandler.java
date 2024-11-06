package br.com.amplitude.admin.catalog.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler aHandler);

    ValidationHandler validate(Validation aValidation);

    List<Error> getErrors();

    default boolean hasErrors() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        if (hasErrors()) {
            return this.getErrors().get(0);
        }
        return null;
    }

    interface Validation {

        void validate();
    }
}
