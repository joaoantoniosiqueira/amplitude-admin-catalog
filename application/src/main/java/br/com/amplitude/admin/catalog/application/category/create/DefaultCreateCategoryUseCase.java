package br.com.amplitude.admin.catalog.application.category.create;

import br.com.amplitude.admin.catalog.domain.category.Category;
import br.com.amplitude.admin.catalog.domain.category.CategoryGateway;
import br.com.amplitude.admin.catalog.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CreateCategoryOutput execute(final CreateCategoryCommand aCommand) {
        final var name = aCommand.name();
        final var description = aCommand.description();
        final var isActive = aCommand.isActive();

        final var aCategory = Category.newCategory(name, description, isActive);
        aCategory.validate(new ThrowsValidationHandler());

        this.categoryGateway.create(aCategory);
        return CreateCategoryOutput.from(aCategory.getId());
    }
}
