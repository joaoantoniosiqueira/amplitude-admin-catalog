package br.com.amplitude.admin.catalog.application.category.update;

import br.com.amplitude.admin.catalog.application.UseCase;
import br.com.amplitude.admin.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
