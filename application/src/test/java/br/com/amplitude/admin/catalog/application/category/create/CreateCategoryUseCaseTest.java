package br.com.amplitude.admin.catalog.application.category.create;

import br.com.amplitude.admin.catalog.domain.category.CategoryGateway;
import br.com.amplitude.admin.catalog.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallCreateCategory_thenReturnCategoryId() {
        final var expectedName = "Action";
        final var expectedDescription = "Dive into a journey full of adrenaline and adventure with our Action Movies category";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

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

    @Test
    public void givenAInvalidName_whenCallCreateCategory_thenShouldReturnDomainException() {
        final String expectedName = null;
        final var expectedDescription = "Dive into a journey full of adrenaline and adventure with our Action Movies category";
        final var expectedIsActive = true;

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.lenient().when(categoryGateway.create(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        Mockito.verify(categoryGateway, Mockito.times(0)).create(Mockito.any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallCreateCategory_thenShouldReturnInactiveCategoryId() {
        final var expectedName = "Action";
        final var expectedDescription = "Dive into a journey full of adrenaline and adventure with our Action Movies category";
        final var expectedIsActive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1)).create(ArgumentMatchers.argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName()) &&
                        Objects.equals(expectedDescription, aCategory.getDescription()) &&
                        Objects.equals(expectedIsActive, aCategory.isActive()) &&
                        Objects.nonNull(aCategory.getCreatedAt()) &&
                        Objects.nonNull(aCategory.getUpdatedAt()) &&
                        Objects.nonNull(aCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_thenShouldReturnAException() {
        final var expectedName = "Action";
        final var expectedDescription = "Dive into a journey full of adrenaline and adventure with our Action Movies category";
        final var expectedIsActive = true;

        final var expectedErrorMessage = "Gateway error";

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(Mockito.any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        Mockito.verify(categoryGateway, Mockito.times(1)).create(ArgumentMatchers.argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName()) &&
                        Objects.equals(expectedDescription, aCategory.getDescription()) &&
                        Objects.equals(expectedIsActive, aCategory.isActive()) &&
                        Objects.nonNull(aCategory.getCreatedAt()) &&
                        Objects.nonNull(aCategory.getUpdatedAt()) &&
                        Objects.isNull(aCategory.getDeletedAt())
        ));
    }
}
