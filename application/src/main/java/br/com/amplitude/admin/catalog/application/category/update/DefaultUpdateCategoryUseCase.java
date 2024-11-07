package br.com.amplitude.admin.catalog.application.category.update;

import br.com.amplitude.admin.catalog.domain.category.Category;
import br.com.amplitude.admin.catalog.domain.category.CategoryGateway;
import br.com.amplitude.admin.catalog.domain.category.CategoryID;
import br.com.amplitude.admin.catalog.domain.exceptions.DomainException;
import br.com.amplitude.admin.catalog.domain.validation.Error;
import br.com.amplitude.admin.catalog.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(CategoryGateway categoryGateway) {
        Objects.requireNonNull(categoryGateway);
        this.categoryGateway = categoryGateway;
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand aCommand) {
        final var anId = CategoryID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var aCategory = this.categoryGateway.findById(anId)
                .orElseThrow(() -> notFound(anId));

        final var notification = Notification.create();

        aCategory
                .update(aName, aDescription, isActive)
                .validate(notification);

        return notification.hasErrors() ? API.Left(notification) : update(aCategory);
    }

    public Either<Notification, UpdateCategoryOutput> update(final Category aCategory) {
        return API.Try(() -> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }

    private static DomainException notFound(CategoryID anId) {
        return DomainException.with(new Error("Category with ID %s was not found".formatted(anId.getValue())));
    }
}
