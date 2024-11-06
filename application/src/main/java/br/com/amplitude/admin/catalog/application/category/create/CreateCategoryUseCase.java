package br.com.amplitude.admin.catalog.application.category.create;

import br.com.amplitude.admin.catalog.application.UseCase;
import br.com.amplitude.admin.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
