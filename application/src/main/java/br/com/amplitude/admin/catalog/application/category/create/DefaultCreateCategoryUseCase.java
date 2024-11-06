package br.com.amplitude.admin.catalog.application.category.create;

import br.com.amplitude.admin.catalog.domain.category.Category;
import br.com.amplitude.admin.catalog.domain.category.CategoryGateway;
import br.com.amplitude.admin.catalog.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand aCommand) {
        final var name = aCommand.name();
        final var description = aCommand.description();
        final var isActive = aCommand.isActive();

        final var notification = Notification.create();

        final var aCategory = Category.newCategory(name, description, isActive);
        aCategory.validate(notification);

        return notification.hasErrors() ? API.Left(notification) : create(aCategory);
    }

    private Either<Notification, CreateCategoryOutput> create(final Category aCategory) {
        return API.Try(() -> this.categoryGateway.create(aCategory))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
