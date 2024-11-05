package br.com.amplitude.admin.catalog.application.category.create;

import br.com.amplitude.admin.catalog.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @Test
    public void givenAValidCommand_whenCallCreateCategory_thenReturnCategoryId() {
        final var expectedName = "Action";
        final var expectedDescription = "Dive into a journey full of adrenaline and adventure with our Action Movies category";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .create(Mockito.argThat(aCategory ->
                        Objects.equals(expectedName, aCategory.getName()) &&
                                Objects.equals(expectedDescription, aCategory.getDescription()) &&
                                Objects.equals(expectedIsActive, aCategory.isActive()) &&
                                Objects.nonNull(aCategory.getCreatedAt()) &&
                                Objects.nonNull(aCategory.getUpdatedAt()) &&
                                Objects.isNull(aCategory.getDeletedAt())
                ));
    }
}
