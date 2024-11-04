package br.com.amplitude.admin.catalog.domain.category;

import br.com.amplitude.admin.catalog.domain.validation.Error;
import br.com.amplitude.admin.catalog.domain.validation.ValidationHandler;
import br.com.amplitude.admin.catalog.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    protected CategoryValidator(final Category aCategory, final ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.category.getName();
        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final var length = name.trim().length();
        if (length < 3 || length > 255) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}
